package com.traveloper.tourfinder.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.service.MemberService;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.oauth2.dto.KakaoTokenResponse;
import com.traveloper.tourfinder.oauth2.dto.KakaoUserProfile;
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
import org.springframework.web.client.RestTemplate;

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
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoUserInfoUri;

    public String getKakaoLoginUrl() {
        String path = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
        return path;
    }


    @Transactional
    public ResponseEntity<MemberDto> kakaoLogin(String code) {
        String accessToken = getAccessTokenUsingCode(code).getAccess_token();
        KakaoUserProfile userInfo = getUserInfoUsingAccessToken(accessToken);
        String email = userInfo.getKakaoAccount().getEmail();

        Optional<Member> member = memberRepository.findMemberByEmail(email);

        // 이미 존재하는 멤버라면 연동처리
        if (member.isPresent()) {
            linkAccountWithSocialLogin(email,"Kakao");

            return ResponseEntity.status(200).body(
                    MemberDto.builder()
                            .nickname(member.get().getNickname())
                            .email(member.get().getEmail())
                            .memberName(member.get().getMemberName())
                            .role(member.get().getRole().getName())
                            .uuid(member.get().getUuid())
                            .build()
            );
        } else {
            // 존재하지 않는 멤버라면 회원가입 처리

            String nickname = userInfo.getProperties().getNickname();
            String password = passwordEncoder.encode(UUID.randomUUID().toString());

            CreateMemberDto createMemberDto = CreateMemberDto.builder()
                    .nickname(nickname)
                    .email(email)
                    .password(password)
                    .build();


            return ResponseEntity.status(201).body(
                    memberService.signup(createMemberDto)
            );
        }


        // TODO: 카카오 로그인 기능 구현
        // TODO: 존재하는 유저라면 연동처리
        // TODO: 존재하지 않는 유저라면 회원가입 처리

    }

    public void signUp() {
        // TODO: 소셜 회원가입
    }

    ;

    /**
     * <p>
     * 소셜 로그인 이후 받은 code로 AccessToken을 발급 하는 메서드
     * </p>
     */
    public KakaoTokenResponse getAccessTokenUsingCode(String code) {
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
            return mapper.readValue(response.getBody(), KakaoTokenResponse.class);
        } catch (JsonProcessingException e) {
            log.warn("카카오 엑세스 토큰 받아온 후, 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

    }

    public KakaoUserProfile getUserInfoUsingAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(kakaoUserInfoUri, request, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), KakaoUserProfile.class);
        } catch (JsonProcessingException e) {
            log.warn("카카오 유저 정보 조회 후, 데이터 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

    }

    public void linkAccountWithSocialLogin(
            String email,
            String socialProviderName

    ) {

    };
}
