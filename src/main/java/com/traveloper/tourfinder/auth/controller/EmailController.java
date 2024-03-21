package com.traveloper.tourfinder.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/auth")
public class EmailController {

    @GetMapping("/email/verify")
    public void sendEmail(){
        // TODO: 이메일 전송 기능
        // TODO: 랜덤 코드 생성
        // TODO: Redis에 key(email) : value(code) 로 저장
    }

    @PostMapping("/email/verify-code")
    public void verifyCode(){
        // TODO: 이메일 인증코드 진위여부 확인
        // TODO: Redis에 key(email)가 존재하는지 확인 후 존재한다면 value(code) 비교
    }




}
