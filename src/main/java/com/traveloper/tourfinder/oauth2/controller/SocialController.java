package com.traveloper.tourfinder.oauth2.controller;


import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.oauth2.service.GoogleOauthService;
import com.traveloper.tourfinder.oauth2.service.KakaoOauthService;
import com.traveloper.tourfinder.oauth2.service.NaverOauthService;
import com.traveloper.tourfinder.oauth2.service.SocialOauthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

/**
 * <p>소셜 로그인 및 관련 기능 컨트롤러 입니다.</p>
 * */

@RestController
@RequestMapping("api/v1/oauth2")
@AllArgsConstructor
public class SocialController {
    private final GoogleOauthService googleOauthService;
    private final NaverOauthService naverOauthService;
    private final KakaoOauthService kakaoOauthService;
    private final SocialOauthService socialOauthService;

    @GetMapping("/{socialProviderName}/authorize")
    public ResponseEntity<TokenDto> oauth2Authorize(
            @RequestParam("token")
            String token
    ){
        TokenDto tokenDto = socialOauthService.checkAuthorizeToken(token);
        return ResponseEntity.ok(tokenDto);
    }


    @GetMapping("/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String path = kakaoOauthService.getKakaoLoginUrl();
        response.sendRedirect(path);
    }

    @GetMapping("/naver")
    public void naverLogin(HttpServletResponse response) throws IOException {
        String path = naverOauthService.getNaverLoginUrl();
        response.sendRedirect(path);
    }

    @GetMapping("/google")
    public void googleLogin(HttpServletResponse response) throws IOException {
        String path = googleOauthService.getGoogleLoginUrl();
        response.sendRedirect(path);
    }

    @GetMapping("/google/callback")
    public void redirectSocialGoogle(
            @RequestParam("code")
            String code,
            HttpServletResponse response
    ) throws IOException {

        String redirectPath = googleOauthService.googleLogin(code);
        response.sendRedirect(redirectPath);
    }

    @GetMapping("/kakao/callback")
    public void redirectSocialKakao(
            @RequestParam("code")
            String code,
            HttpServletResponse response
    ) throws IOException {

        String redirectPath = kakaoOauthService.kakaoLogin(code);
        response.sendRedirect(redirectPath);
    }

    @GetMapping("/naver/callback")
    public void redirectSocialNaver(
            @RequestParam("code")
            String code,
            HttpServletResponse response
    ) throws IOException  {
        String redirectPath = naverOauthService.naverLogin(code);
        // TODO: 네이버 로그인 클릭 시 리다이렉트 되는 url
        response.sendRedirect(redirectPath);
    }
}
