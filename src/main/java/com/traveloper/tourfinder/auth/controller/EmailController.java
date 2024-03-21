package com.traveloper.tourfinder.auth.controller;

import com.traveloper.tourfinder.auth.dto.VerifyCodeSendSuccessDto;
import com.traveloper.tourfinder.auth.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "email", description = "이메일 인증코드 전송")
@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/email/verify")
    public void sendEmail(
            @RequestParam("email")
            String email
    ){
        // TODO: 랜덤 코드 생성
        // TODO: Redis에 key(email) : value(code) 로 저장
        VerifyCodeSendSuccessDto result = emailService.sendVerifyCodeMail(email);
        log.info(result.getEmail() + "이메일");
        log.info(result.getCode() + "코드");
    }

    @PostMapping("/email/verify-code")
    public void verifyCode(){
        // TODO: 이메일 인증코드 진위여부 확인
        // TODO: Redis에 key(email)가 존재하는지 확인 후 존재한다면 value(code) 비교
    }




}
