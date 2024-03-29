package com.traveloper.tourfinder.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.oauth2.dto.KakaoTokenResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Service
public class KakaoOauthService {
    /**
     *
     * <p> 소셜 로그인 기능구현시 주의사항 <br />
     * 1. 소셜 로그인 후 가져온 이메일이 이미 DB에 있다면 단순히 연동처리 <br />
     * 2. 소셜 로그인 연동 회원은 비밀번호 변경을 할 수 없습니다. ( 소셜 사업자 쪽에서 비밀번호 인증을 처리하기 때문에 비번 변경 비활성화 ) <br/>
     * 3. 마이페이지 비밀번호 변경 버튼 및 기능 비활성화 <br />
     * 4. 비밀번호 분실시 비밀번호 초기화를 하는데 소셜로그인 연동시 이 기능도 사용할 수 없도록 처리 <br />
     * 5. 어떤 소셜 로그인을 연동했는지 마이페이지 등 특정 부분에 표시 <br />
     * </p>
     *
     *
     *
     *
     *
     * */

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    public String getKakaoLoginUrl(){
        String path = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUri;
        return path;
    }


    @Transactional
    public void kakaoLogin(String code) throws JsonProcessingException {

        getAccessToken(code);
        // TODO: 카카오 로그인 기능 구현
        // TODO: 엑세스 토큰 요청
        // TODO: 엑세스 토큰으로 유저 정보 요청
        // TODO: 엑세스 토큰으로 받아온 데이터 중 이메일을 MemberRepository에서 조회
        // TODO: 존재하는 유저라면 연동처리
        // TODO: 존재하지 않는 유저라면 회원가입 처리

    }

    public void signUp(){
        // TODO: 소셜 회원가입
    };
    public String getAccessToken(String code) throws JsonProcessingException {
        // TODO 받아온 code 를 통해 accessToken 요청
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
            KakaoTokenResponse tokenResponse = mapper.readValue(response.getBody(), KakaoTokenResponse.class);

            return tokenResponse.getAccess_token();
        } catch (JsonProcessingException e) {
            log.warn("직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

    }
}
