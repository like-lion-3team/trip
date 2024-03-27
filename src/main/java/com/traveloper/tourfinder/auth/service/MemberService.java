package com.traveloper.tourfinder.auth.service;


import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.dto.SignInDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.entity.CustomUserDetails;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.entity.Role;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.repo.RoleRepository;
import com.traveloper.tourfinder.common.AppConstants;
import com.traveloper.tourfinder.common.RedisRepo;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import com.traveloper.tourfinder.common.util.RandomCodeUtils;
import com.traveloper.tourfinder.course.service.CourseService;
import jakarta.transaction.Transactional;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
    private final AuthenticationFacade authenticationFacade;


    /**
     * 회원가입
     *
     * @param dto 유저가 입력한 닉네임, 이메일, 비밀번호
     */
    @Transactional
    public MemberDto signup(
            // 닉네임, 이메일, 비밀번호 입력받기
            CreateMemberDto dto
    ) {
        // 닉네임 중복체크, 이메일 중복체크
        if (memberRepository.existsByEmail(dto.getEmail()) || memberRepository.existsByNickname(dto.getNickname()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

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

    public TokenDto login(
            SignInDto dto
    ) {

        Member member = memberRepository.findMemberByEmail(dto.getEmail()).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.CREDENTIALS_NOT_MATCH)
        );

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new AccessDeniedException("로그인 실패");
        }

        if(member.getRole().getName().equals("BLOCK_USER")){
            throw  new AccessDeniedException("차단된 사용자");
        }



        String accessToken = jwtTokenUtils.generateToken(member, AppConstants.ACCESS_TOKEN_EXPIRE_SECOND);
        String refreshToken = jwtTokenUtils.generateToken(member, AppConstants.REFRESH_TOKEN_EXPIRE_SECOND);
        redisRepo.saveRefreshToken(accessToken,refreshToken);

        if(redisRepo.getRefreshToken(accessToken).isEmpty()){
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

        return TokenDto.builder()
                .accessToken(accessToken)
                .expiredDate(LocalDateTime.now().plusSeconds(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND))
                .expiredSecond(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND)
                .build();
    }

    public void signOut(String accessToken){
        redisRepo.destroyRefreshToken(accessToken);
    }

    // 비밀번호 수정
    public void updatePassword(String email, String newPassword) {
        Optional<Member> member = memberRepository.findMemberByEmail(email);
//        if (member != null) {
//            member.setPassword(newPassword);
//            memberRepository.save(member);
    }


    /**
     * <p>이메일 인증용 코드 전송</p>
     */
    public void sendCode(
            String email
    ) {
        // TODO: 이메일 인증 - 받아온 이메일로 인증코드 전송
        // TODO: 이메일 인증 - Redis에 이메일 (key) : 코드 (value)  형태로 값 저장
        String key = String.valueOf(redisRepo.getVerifyCode(email));
        String value = RandomCodeUtils.generate(6);

        redisRepo.saveVerifyCode(key, value);


    }

    public ResponseEntity<String> verifyCode(
            String email,
            String code
    ) {
        // TODO: 인증 코드 검증 - 유저가 입력한 코드 검사
        // TODO: 인증 코드 검증 - Redis에 저장된 값 ( 이메일 )을 검색
        // TODO: 인증 코드 검증 - 일치하면 200 응
        String savedCode = redisRepo.saveVerifyCode(email,code);

        if (savedCode != null && savedCode.equals(code)) {
            return ResponseEntity.ok("코드 일치");
        } else {
            return ResponseEntity.badRequest().body("코드 불일치");
        }
    }

    public MemberDto findMember(String uuid){
        Member member = memberRepository.findMemberByUuid(uuid).orElseThrow(
                () -> new AccessDeniedException("유저를 찾을 수 없습니다.")
        );

        return MemberDto.builder()
                .id(null)
                .uuid(member.getUuid())
                .email(member.getEmail())
                .memberName(member.getMemberName())
                .nickname(member.getNickname())
                .role(member.getRole().getName())
                .build();


    }

    public boolean isPossibleSendCode(
            String email
    ) {
        // TODO ( optional ) : 이메일 인증 - 당일 요청 가능한 횟수 제한 체크
        return true;
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

