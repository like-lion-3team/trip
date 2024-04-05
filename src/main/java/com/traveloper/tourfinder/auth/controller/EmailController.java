package com.traveloper.tourfinder.auth.controller;

import com.traveloper.tourfinder.auth.dto.VerifyCodeSendSuccessDto;
import com.traveloper.tourfinder.auth.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "email", description = "이메일 인증코드 전송")
@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/email/{email}/verify")
    public ResponseEntity sendEmail(
            @PathVariable("email")
            String email
    ){
        VerifyCodeSendSuccessDto result = emailService.sendVerifyCodeMail(email);
        log.info(result.getEmail() + "이메일");
        log.info(result.getCode() + "코드");
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/email/{email}/verify-code/{code}")
    public ResponseEntity verifyCode(
            @PathVariable("email")
            String email,
            @PathVariable("code")
            String code
    ){
         boolean result = emailService.verifyCode(email,code);
         return result ? ResponseEntity.ok("{}") : ResponseEntity.status(400).body("{}");
    }




}
