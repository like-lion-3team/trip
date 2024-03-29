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
    public TokenDto handleNewUser( String socialProviderName,String nickname , String email) {
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
        return generateTokenDto(findMember.getUuid());
    }


    /**
     * <p>이미 존재하는 유저 연동</p>
     * */
    public TokenDto handleExistingUser(String socialProviderName, Member member) {
        boolean isLinkedSocial = member.getSocialProviderMembers().stream()
                .anyMatch(spm -> socialProviderName.equals(spm.getSocialProvider().getSocialProviderName()));

        // 타겟 플랫폼 연동 체크
        if (!isLinkedSocial) {
            linkAccountWithSocialLogin(socialProviderName, member);
        }else{
            handleNewUser(socialProviderName, member.getNickname(), member.getEmail());
        }

        return generateTokenDto(member.getUuid());
    }

    /**
     * <p>엑세스 토큰을 생성하는 메서드</p>
     * */
    private TokenDto generateTokenDto(String uuid) {
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
}
