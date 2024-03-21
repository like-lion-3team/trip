package com.traveloper.tourfinder.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class TokenController {
    @GetMapping("/token")
    public void reissuanceAccessToken(){
        // TODO: AccessToken 재발급
    }

    @GetMapping("/destroy")
    public void destroyToken(){
        // TODO: AccessToken, RefreshToken 삭제
    }
}
