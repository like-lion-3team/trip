package com.traveloper.tourfinder.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.oauth2.dto.GoogleTokenResponse;
import com.traveloper.tourfinder.oauth2.dto.GoogleUserProfile;
import com.traveloper.tourfinder.oauth2.dto.KakaoTokenResponse;
import com.traveloper.tourfinder.oauth2.dto.KakaoUserProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOauthService {
    private final MemberRepository memberRepository;
    private final SocialOauthService socialOauthService;

    private final String SOCIAL_PROVIDER_NAME = "GOOGLE";

    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String googleTokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUri;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    public String getGoogleLoginUrl() {
        String path = authorizationUri + "?response_type=code" + "&client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=" + "email profile";
        return path;
    }

    @Transactional
    public String googleLogin(
            String code
    ) {
        System.out.printf(code + "코드");
        String googleAccessToken = getAccessToken(code).getAccessToken();
        System.out.printf(googleAccessToken + "구글 액세스 토큰");
        GoogleUserProfile userInfo = getProfile(googleAccessToken);
        String email = userInfo.getEmail();
        String nickname = userInfo.getName();

        Optional<Member> memberOpt = memberRepository.findMemberByEmail(email);
        if (memberOpt.isEmpty()) {
            // 사용자가 존재하지 않으면 회원가입 및 연동 후 토큰 전달
            MemberDto memberDto = socialOauthService.handleNewUser(SOCIAL_PROVIDER_NAME,nickname, email);
            return socialOauthService.getRedirectPathAndSaveOauth2AuthorizeToken(SOCIAL_PROVIDER_NAME, memberDto);
        } else {
            // 존재하는 사용자라면 연동 처리
            MemberDto memberDto = socialOauthService.handleExistingUser(SOCIAL_PROVIDER_NAME,memberOpt.get());
            return socialOauthService.getRedirectPathAndSaveOauth2AuthorizeToken(SOCIAL_PROVIDER_NAME, memberDto);
        }

    }

    public GoogleTokenResponse getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        System.out.printf("엑세스토큰 생성시 사용할 코드" + code);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setCacheControl(CacheControl.noCache());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("redirect_uri", redirectUri);
        map.add("code", code);
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(googleTokenUri, request, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), GoogleTokenResponse.class);
        } catch (JsonProcessingException e) {
            System.out.printf(e.getMessage());
            log.warn("구글 엑세스 토큰 받아온 후, 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }
    }

    public GoogleUserProfile getProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);


        HttpEntity<String> request = new HttpEntity<>(headers);
        System.out.println(request.getHeaders().get("Authorization") + "    인증토큰");

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    googleUserInfoUri, HttpMethod.GET, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), GoogleUserProfile.class);
        } catch (HttpClientErrorException e) {
            log.warn("클라이언트 오류: " + e.getStatusCode());
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        } catch (HttpServerErrorException e) {
            log.warn("서버 오류: " + e.getStatusCode());
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        } catch (JsonProcessingException e) {
            System.out.printf(e.getMessage());
            log.warn("구글 유저 정보 조회 후, 데이터 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }
    }
}

