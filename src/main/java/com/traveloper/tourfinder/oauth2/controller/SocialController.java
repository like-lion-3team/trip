package com.traveloper.tourfinder.oauth2.controller;


import com.traveloper.tourfinder.oauth2.service.KakaoOauthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>소셜 로그인 및 관련 기능 컨트롤러 입니다.</p>
 * */

@RestController
@RequestMapping("api/v1/oauth2")
@AllArgsConstructor
public class SocialController {
    private final KakaoOauthService kakaoOauthService;


    @GetMapping("/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String path = kakaoOauthService.getKakaoLoginUrl();
        response.sendRedirect(path);
    }

    @GetMapping("/naver")
    public void naverLogin(){

    }

    @GetMapping("/kakao/callback")
    public void redirectSocialKakao(
            @RequestParam("code")
            String code
    ){
        // TODO: 카카오 로그인 클릭시 리다이렉트 되는 url
//        kakaoOauthService.kakaoLogin(code);
    }

    @GetMapping("/naver/callback")
    public void redirectSocialNaver(){
        // TODO: 네이버 로그인 클릭 시 리다이렉트 되는 url
    }
}
