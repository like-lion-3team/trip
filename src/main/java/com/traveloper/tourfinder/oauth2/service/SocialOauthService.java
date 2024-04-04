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
import com.traveloper.tourfinder.oauth2.dto.KakaoUserProfile;
import com.traveloper.tourfinder.oauth2.dto.NaverTokenResponse;
import com.traveloper.tourfinder.oauth2.dto.NaverUserProfile;
import com.traveloper.tourfinder.oauth2.dto.SocialProviderAccessTokenRequestDto;
import com.traveloper.tourfinder.oauth2.entity.SocialProvider;
import com.traveloper.tourfinder.oauth2.entity.SocialProviderMember;
import com.traveloper.tourfinder.oauth2.repo.SocialProviderMemberRepo;
import com.traveloper.tourfinder.oauth2.repo.SocialProviderRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        
        MemberDto memberDto = memberService.signup(createMemberDto, "SOCIAL");
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
                .profile(member.getProfile())
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
                .uuid(uuid)
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
        redisRepo.saveOauth2AuthorizeToken(randomUUID,generateTokenDto(memberDto.getUuid())  );
        return "/oauth2/callback?socialProvider=" + socialProviderName + "&" + "token=" + randomUUID;
    }

    // TODO : redis 에서 임시 토큰 조회하는 서비스 구현
    public TokenDto checkAuthorizeToken(String token){
        return redisRepo.getOauth2AuthorizeToken(token).orElseThrow(
                () -> new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE)
        );

    }

    public <T> T getSocialProviderAccessTokenRequest(SocialProviderAccessTokenRequestDto dto,String socialProviderName, Class<T> socialProviderTokenResponse){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setCacheControl(CacheControl.noCache());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", dto.getClientId());
        map.add("redirect_uri", dto.getRedirectUri());
        map.add("code", dto.getCode());
        map.add("client_secret", dto.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(dto.getSocialTokenUri(), request, String.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), socialProviderTokenResponse);
        } catch (JsonProcessingException e) {
            log.warn(socialProviderName + " 엑세스 토큰 받아온 후, 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }
    }

    public <T> T getProfileRequest(String accessToken, String requestUri, Class<T> userProfileClass){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);


        HttpEntity<String> request = new HttpEntity<>(headers);
        System.out.println(request.getHeaders().get("Authorization") + "    인증토큰");
        ResponseEntity<String> response = restTemplate.exchange(
                requestUri, HttpMethod.GET, request, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), userProfileClass);
        } catch (HttpClientErrorException e) {
            log.warn("클라이언트 오류: " + e.getStatusCode());
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        } catch (HttpServerErrorException e) {
            log.warn("서버 오류: " + e.getStatusCode());
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        } catch (JsonProcessingException e) {
            log.warn("네이버 유저 정보 조회 후, 데이터 직렬화 에러");
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SERVICE_UNAVAILABLE);
        }

    }
}
