package com.traveloper.tourfinder.oauth2.service;

import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.service.MemberService;
import com.traveloper.tourfinder.common.AppConstants;
import com.traveloper.tourfinder.common.RedisRepo;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.oauth2.dto.KakaoUserProfile;
import com.traveloper.tourfinder.oauth2.entity.SocialProvider;
import com.traveloper.tourfinder.oauth2.entity.SocialProviderMember;
import com.traveloper.tourfinder.oauth2.repo.SocialProviderMemberRepo;
import com.traveloper.tourfinder.oauth2.repo.SocialProviderRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialOauthService {
    private final MemberService memberService;

    private final RedisRepo redisRepo;
    private final MemberRepository memberRepository;
    private final SocialProviderRepo socialProviderRepo;
    private final SocialProviderMemberRepo socialProviderMemberRepo;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;

    /**
     * <p>소셜 로그인시 기존 계정과 소셜 계정을 연동하는 메서드 </p>
     */
    public void linkAccountWithSocialLogin(String socialProviderName, Member member) {

        // DB에 소셜 사업자 이름이 정의되어있지 않으면 에러 발생시킴
        SocialProvider socialProvider = socialProviderRepo.findSocialProviderBySocialProviderName(socialProviderName).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER));

        SocialProviderMember socialProviderMember = SocialProviderMember.builder()
                .member(member)
                .socialProvider(socialProvider)
                .build();

        try {
            socialProviderMemberRepo.save(socialProviderMember);
        } catch (RuntimeException e) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }
    }


    /**
     * <p>새로운 유저를 생성하고 해당 유저를 소셜로그인 연동</p>
     * */
    public MemberDto handleNewUser( String socialProviderName,String nickname , String email) {
        String password = passwordEncoder.encode(UUID.randomUUID().toString());

        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();

        MemberDto memberDto = memberService.signup(createMemberDto);
        Member findMember = memberRepository.findMemberByUuid(memberDto.getUuid()).orElseThrow(
                () -> {
                    log.warn("회원 가입이 정상 처리되지 않았습니다.");
                    return new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
                });

        linkAccountWithSocialLogin(socialProviderName, findMember);
        return memberDto;

    }


    /**
     * <p>이미 존재하는 유저 연동</p>
     * */
    public MemberDto handleExistingUser(String socialProviderName, Member member) {
        boolean isLinkedSocial = member.getSocialProviderMembers().stream()
                .anyMatch(spm -> socialProviderName.equals(spm.getSocialProvider().getSocialProviderName()));
        log.info("소셜 회원인지 체크:"+ isLinkedSocial);
        // 타겟 플랫폼 연동 체크
        if (!isLinkedSocial) {
            linkAccountWithSocialLogin(socialProviderName, member);
        }

        return MemberDto.builder()
                .uuid(member.getUuid())
                .nickname(member.getNickname())
                .role(member.getRole().toString())
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .build();
    }

    /**
     * <p>엑세스 토큰을 생성하는 메서드</p>
     * */
    public TokenDto generateTokenDto(String uuid) {
        String accessToken = jwtTokenUtils.generateToken(uuid, AppConstants.ACCESS_TOKEN_EXPIRE_SECOND);
        String refreshToken = jwtTokenUtils.generateToken(uuid, AppConstants.REFRESH_TOKEN_EXPIRE_SECOND);
        redisRepo.saveRefreshToken(accessToken,refreshToken);

        if (redisRepo.getRefreshToken(accessToken).isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

        return TokenDto.builder()
                .accessToken(accessToken)
                .expiredDate(LocalDateTime.now().plusSeconds(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND))
                .expiredSecond(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND)
                .build();
    }

    /**
     * <p>임시 인증용 토큰 생성 및 path 생성 메서드 입니다.</p>
     * @param socialProviderName 소셜 사업자 종류 입니다. 대문자로 입력 해주세요. ex) KAKAO, NAVER. DB에 대문자로 저장중
     * @param memberDto 멤버 Dto입니다. 멤버고유의 uuid를 얻을 목적입니다.
     * */
    public String getRedirectPathAndSaveOauth2AuthorizeToken(String socialProviderName, MemberDto memberDto){
        UUID randomUUID = UUID.randomUUID();

        System.out.printf(memberDto.getUuid() + "UUID");
        System.out.printf(randomUUID + "UUID");
        redisRepo.saveOauth2AuthorizeToken(randomUUID,generateTokenDto(memberDto.getUuid())  );
        return "/oauth2/callback?socialProvider=" + socialProviderName + "&" + "token=" + randomUUID;
    }

    // TODO : redis 에서 임시 토큰 조회하는 서비스 구현
    public TokenDto checkAuthorizeToken(String token){
        return redisRepo.getOauth2AuthorizeToken(token).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE)
        );

    }
}
