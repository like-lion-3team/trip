package com.traveloper.tourfinder.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.oauth2.dto.*;
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

        GoogleTokenResponse tokenResponse = socialOauthService.getSocialProviderAccessTokenRequest(
                SocialProviderAccessTokenRequestDto.builder()
                        .code(code)
                        .socialTokenUri(googleTokenUri)
                        .grantType("authorization_code")
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .redirectUri(redirectUri)
                        .build(),
                SOCIAL_PROVIDER_NAME,
                GoogleTokenResponse.class
        );

        GoogleUserProfile userInfo = socialOauthService.getProfileRequest(tokenResponse.getAccessToken(),googleUserInfoUri,GoogleUserProfile.class);
        String email = userInfo.getEmail();
        String nickname = userInfo.getEmail() + "_google";

        Optional<Member> memberOpt = memberRepository.findMemberByEmail(email);
        if (memberOpt.isEmpty()) {
            MemberDto memberDto = socialOauthService.handleNewUser(SOCIAL_PROVIDER_NAME,nickname, email);
            return socialOauthService.getRedirectPathAndSaveOauth2AuthorizeToken(SOCIAL_PROVIDER_NAME, memberDto);
        } else {
            MemberDto memberDto = socialOauthService.handleExistingUser(SOCIAL_PROVIDER_NAME,memberOpt.get());
            return socialOauthService.getRedirectPathAndSaveOauth2AuthorizeToken(SOCIAL_PROVIDER_NAME, memberDto);
        }

    }




}


