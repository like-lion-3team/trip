package com.traveloper.tourfinder.auth.service;


import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.dto.MemberIdDto;
import com.traveloper.tourfinder.auth.dto.SignInDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.dto.VerifyCodeSendSuccessDto;
import com.traveloper.tourfinder.auth.entity.CustomUserDetails;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.entity.Role;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.password.InvalidPasswordException;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.repo.RoleRepository;
import com.traveloper.tourfinder.common.AppConstants;
import com.traveloper.tourfinder.common.RedisRepo;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RedisRepo redisRepo;
    private final EmailService emailService;
    private final AuthenticationFacade authenticationFacade;


    /**
     * 회원가입
     *
     * @param dto 유저가 입력한 닉네임, 이메일, 비밀번호
     */
    @Transactional
    public MemberDto signup(
            // 닉네임, 이메일, 비밀번호 입력받기
            CreateMemberDto dto,

            // COMMON, SOCIAL
            String type
    ) {
        // 닉네임 중복체크, 이메일 중복체크
        if (memberRepository.existsByEmail(dto.getEmail())){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.EMAIL_ALREADY_EXIST);
        } else if (memberRepository.existsByNickname(dto.getNickname()) ){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.NICKNAME_ALREADY_EXIST);
        } else if (type.equals("COMMON") && redisRepo.getVerifyCode(dto.getEmail()).isEmpty() ){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.PASSWORD_RECOVERY_CODE_MISS_MATCH);
        }


        String uuid = UUID.randomUUID().toString();

        Optional<Role> optionalRole = roleRepository.findById(1L);
        Role role = optionalRole.get();

        return MemberDto.fromEntity(memberRepository.save(Member.builder()
                .uuid(uuid)
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                // 비밀번호 저장시 암호화
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build()));
    }

    public boolean nicknameDuplicateCheck(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    public boolean isLogin(String authorization){
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.split(" ")[1];
            return jwtTokenUtils.validate(token);
        }
        return false;

    }

    public TokenDto login(
            SignInDto dto
    ) {

        Member member = memberRepository.findMemberByEmail(dto.getEmail()).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.CREDENTIALS_NOT_MATCH)
        );

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.CREDENTIALS_NOT_MATCH);
        }

        if(member.getRole().getName().equals("BLOCK_USER")){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.USER_BLOCKED);

        }



        String accessToken = jwtTokenUtils.generateToken(member, AppConstants.ACCESS_TOKEN_EXPIRE_SECOND);
        String refreshToken = jwtTokenUtils.generateToken(member, AppConstants.REFRESH_TOKEN_EXPIRE_SECOND);
        redisRepo.saveRefreshToken(accessToken,refreshToken);

        if(redisRepo.getRefreshToken(accessToken).isEmpty()){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

        return TokenDto.builder()
                .uuid(member.getUuid())
                .accessToken(accessToken)
                .expiredDate(LocalDateTime.now().plusSeconds(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND))
                .expiredSecond(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND)
                .build();
    }

    public void signOut(String accessToken){
        redisRepo.destroyRefreshToken(accessToken);
    }


    /**
     * <p>인증 완료된 후 비밀번호를 변경할 때 사용하는 메서드 <br />
     * 이메일 인증, 휴대폰 인증 등 인증을 완료 한 후에 사용 합니다.
     * </p>
     * */
    @Transactional
    public void updatePassword(String email, String code, String newPassword){
        String verifyCode = redisRepo.getVerifyCode(email).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.NOT_FOUND_PASSWORD_RECOVERY_CODE)
        );

        if(!verifyCode.equals(code)){
          throw new GlobalExceptionHandler(CustomGlobalErrorCode.PASSWORD_RECOVERY_CODE_MISS_MATCH);
        }
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.NOT_FOUND_MEMBER)
        );
        member.updatePassword(passwordEncoder.encode(newPassword));
    }

    /**
     *<p>마이페이지 등에서 현재 비밀번호와 대조한 후 비밀번호를 변경하는 메서드 <br />
     * 이메일 인증, 휴대폰 인증 등 인증과정을 현재 비밀번호로 대신합니다.
     * </p>
     */
    @Transactional
    public void updatePasswordWithCurrentPassword(String email, String currentPassword, String newPassword) {
        Member member = validatePassword(email, currentPassword);
        member.updatePassword(passwordEncoder.encode(newPassword));
    }



    private Member validatePassword(String email, String password) {

        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(InvalidPasswordException::new);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidPasswordException();
        }
        return member;
    }


    /**
     * <p>이메일 인증용 코드 전송</p>
     */
    public void sendCode(
            String email
    ) {
        Optional<Member> member = memberRepository.findMemberByEmail(email);
        if(member.isPresent()){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.EMAIL_ALREADY_EXIST);
        }
        VerifyCodeSendSuccessDto dto = emailService.sendVerifyCodeMail(email);
        String key = String.valueOf(redisRepo.getVerifyCode(dto.getEmail()));

        redisRepo.saveVerifyCode(key, dto.getCode());
    }

    public ResponseEntity<String> verifyCode(
            String email,
            String code
    ) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.NOT_FOUND_MEMBER)
        );

        if(!member.getSocialProviderMembers().isEmpty()){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.MEMBER_IS_SOCIAL_SIGN_UP);
        }

        if (emailService.verifyCode(email,code)) {
            return ResponseEntity.ok("코드 일치");
        } else {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.PASSWORD_RECOVERY_CODE_MISS_MATCH);
        }
    }

    public MemberDto findMember(String uuid){
        Member member = memberRepository.findMemberByUuid(uuid).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.NOT_FOUND_MEMBER)
        );

        return MemberDto.builder()
                .id(null)
                .uuid(member.getUuid())
                .email(member.getEmail())
                .memberName(member.getMemberName())
                .nickname(member.getNickname())
                .profile(member.getProfile())
                .role(member.getRole().getName())
                .build();


    }

    public boolean isPossibleSendCode(
            String email
    ) {
        // TODO ( optional ) : 이메일 인증 - 당일 요청 가능한 횟수 제한 체크
        return true;
    }

    public MemberIdDto getCurrentMemberId() {
        return MemberIdDto.fromMember(authenticationFacade.getCurrentMember());
    }

    public void uploadProfileImage(MultipartFile image) {
        Member currentMember = authenticationFacade.getCurrentMember();

        // 기존 이미지 삭제
        String oldProfile = currentMember.getProfile();
        deleteImage(oldProfile);

        // 새로운 이미지 추가
        String imagePath = saveImage(image);
        imagePath = imagePath.replaceAll("\\\\", "/");
        currentMember.setProfile(imagePath);

        memberRepository.save(currentMember);
    }

    public String saveImage(MultipartFile image) {
        String imgDir = "media/img/profiles/";
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path imgPath = Path.of(imgDir + imgName);

        try {
            Files.createDirectories(Path.of(imgDir));
            image.transferTo(imgPath);
            log.info(image.getName());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return imgPath.toString();
    }

    public void deleteImage(String imagePath) {
        try {
            Files.deleteIfExists(Path.of(imagePath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findMemberByUuid(username);
        if (optionalMember.isEmpty()) throw new UsernameNotFoundException("uuid not found");
        Member member = optionalMember.get();

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }

}

