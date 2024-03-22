package com.traveloper.tourfinder.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "관리자 관련 API 입니다.")
@RestController
@RequestMapping("api/v1/admin")
public class AdminAuthController {

    @PostMapping("/auth/sign-in")
    public void signIn(){
        // TODO: 관리자 로그인 입니다.
    }

    @PatchMapping("/auth/password")
    public void changePassword(){
        // TODO: 관리자 비밀번호 변경.
    }
}
