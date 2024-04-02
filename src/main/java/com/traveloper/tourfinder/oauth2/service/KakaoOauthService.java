package com.traveloper.tourfinder.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.traveloper.tourfinder.oauth2.dto.KakaoTokenResponse;
import com.traveloper.tourfinder.oauth2.dto.KakaoUserProfile;
import com.traveloper.tourfinder.oauth2.dto.SocialProviderAccessTokenRequestDto;
import com.traveloper.tourfinder.oauth2.entity.SocialProvider;
import com.traveloper.tourfinder.oauth2.entity.SocialProviderMember;
import com.traveloper.tourfinder.oauth2.repo.SocialProviderMemberRepo;
import com.traveloper.tourfinder.oauth2.repo.SocialProviderRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauthService {
    /**
     * <p> 소셜 로그인 기능구현시 주의사항 <br />
     * 1. 소셜 로그인 후 가져온 이메일이 이미 DB에 있다면 단순히 연동처리 <br />
     * 2. 소셜 로그인 연동 회원은 비밀번호 변경을 할 수 없습니다. ( 소셜 사업자 쪽에서 비밀번호 인증을 처리하기 때문에 비번 변경 비활성화 ) <br/>
     * 3. 마이페이지 비밀번호 변경 버튼 및 기능 비활성화 <br />
     * 4. 비밀번호 분실시 비밀번호 초기화를 하는데 소셜로그인 연동시 이 기능도 사용할 수 없도록 처리 <br />
     * 5. 어떤 소셜 로그인을 연동했는지 마이페이지 등 특정 부분에 표시 <br />
     * </p>
     */

    private final MemberRepository memberRepository;
    private final SocialOauthService socialOauthService;

    private final String SOCIAL_PROVIDER_NAME = "KAKAO";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    public String getKakaoLoginUrl() {
        String path = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
        return path;
    }


    @Transactional
    public String kakaoLogin(String code) {
        KakaoTokenResponse tokenResponse = socialOauthService.getSocialProviderAccessTokenRequest(
                SocialProviderAccessTokenRequestDto.builder()
                        .code(code)
                        .socialTokenUri(kakaoTokenUri)
                        .grantType("authorization_code")
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .redirectUri(redirectUri)
                        .build(),
                SOCIAL_PROVIDER_NAME,
                KakaoTokenResponse.class
        );
        KakaoUserProfile userInfo = socialOauthService.getProfileRequest(tokenResponse.getAccess_token(),kakaoUserInfoUri,KakaoUserProfile.class);
        String email = userInfo.getKakaoAccount().getEmail();
        String nickname = userInfo.getKakaoAccount().getEmail() + "_kakao";

        Optional<Member> memberOpt = memberRepository.findMemberByEmail(email);
        if (memberOpt.isEmpty()) {
            System.out.println("유저가 존재하지 않음");

            // 사용자가 존재하지 않으면 회원가입 및 연동 후 토큰 전달
            MemberDto memberDto = socialOauthService.handleNewUser(SOCIAL_PROVIDER_NAME,nickname, email);
            System.out.println(memberDto.getMemberName() + "멤버 아이디");
            return socialOauthService.getRedirectPathAndSaveOauth2AuthorizeToken(SOCIAL_PROVIDER_NAME, memberDto);
        } else {
            // 존재하는 사용자라면 연동 처리
            System.out.printf(memberOpt.get().getUuid() + "멤버 UUID");
            MemberDto memberDto = socialOauthService.handleExistingUser(SOCIAL_PROVIDER_NAME,memberOpt.get());
            String path =  socialOauthService.getRedirectPathAndSaveOauth2AuthorizeToken(SOCIAL_PROVIDER_NAME, memberDto);
            return path;
        }


    }
}
