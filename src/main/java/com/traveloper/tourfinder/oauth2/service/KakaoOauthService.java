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
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RedisRepo redisRepo;
    private final SocialProviderRepo socialProviderRepo;
    private final SocialProviderMemberRepo socialProviderMemberRepo;
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
        String kakaoAccessToken = getAccessTokenUsingCode(code).getAccess_token();
        KakaoUserProfile userInfo = getUserInfoUsingAccessToken(kakaoAccessToken);
        String email = userInfo.getKakaoAccount().getEmail();
        String nickname = userInfo.getProperties().getNickname();

        Optional<Member> memberOpt = memberRepository.findMemberByEmail(email);
        if (memberOpt.isEmpty()) {
            // 사용자가 존재하지 않으면 회원가입 및 연동 후 토큰 전달
            socialOauthService.handleNewUser(SOCIAL_PROVIDER_NAME,nickname, email);
        } else {
            // 존재하는 사용자라면 연동 처리
            socialOauthService.handleExistingUser(SOCIAL_PROVIDER_NAME,memberOpt.get());
        }

        UUID uuid = UUID.randomUUID();
        System.out.printf(uuid + "UUID");

        return "/oauth2/callback?socialProvider=" + "KAKAO" + "&" + "token=" + uuid;
    }


    /**
     * <p>
     * 카카오 로그인 이후 받은 code로 AccessToken을 발급 하는 메서드
     * </p>
     */
    public KakaoTokenResponse getAccessTokenUsingCode(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("redirect_uri", redirectUri);
        map.add("code", code);
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(kakaoTokenUri, request, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), KakaoTokenResponse.class);
        } catch (JsonProcessingException e) {
            log.warn("카카오 엑세스 토큰 받아온 후, 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

    }

    /**
     * 카카오 로그인 이후 받은 AccessToken 으로 유저 정보 조회하는 메서드
     * */
    public KakaoUserProfile getUserInfoUsingAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);


        HttpEntity<String> request = new HttpEntity<>(headers);
        System.out.println(request.getHeaders().get("Authorization") + "    인증토큰");

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    kakaoUserInfoUri, HttpMethod.GET, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), KakaoUserProfile.class);
        } catch (HttpClientErrorException e) {
            log.warn("클라이언트 오류: " + e.getStatusCode());
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        } catch (HttpServerErrorException e) {
            log.warn("서버 오류: " + e.getStatusCode());
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        } catch (JsonProcessingException e) {
            log.warn("카카오 유저 정보 조회 후, 데이터 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }
    }


}
