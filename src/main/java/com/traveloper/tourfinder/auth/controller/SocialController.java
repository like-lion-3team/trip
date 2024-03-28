package com.traveloper.tourfinder.auth.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>소셜 로그인 및 관련 기능 컨트롤러 입니다.</p>
 * */

@RestController
@RequestMapping("api/v1/auth/social")
public class SocialController {

    @GetMapping("/kakao")
    public void redirectSocialKakao(){
        // TODO: 카카오 로그인 클릭시 리다이렉트 되는 url
    }

    @GetMapping("/naver")
    public void redirectSocialNaver(){
        // TODO: 네이버 로그인 클릭 시 리다이렉트 되는 url
    }
}
