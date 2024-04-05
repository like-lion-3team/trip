package com.traveloper.tourfinder.admin.controller;

import com.traveloper.tourfinder.admin.service.AdminService;
import com.traveloper.tourfinder.auth.dto.SignInDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin", description = "관리자 관련 API 입니다.")
@RestController
@RequestMapping("api/v1/admin")
@AllArgsConstructor
public class AdminAuthController {
    private AdminService adminService;


    @PostMapping("/auth/sign-in")
    public ResponseEntity<TokenDto> signIn(
            @RequestBody
            SignInDto dto

    ){
        return ResponseEntity.ok(adminService.signIn(dto));
    }

    @PatchMapping("/auth/password")
    public void changePassword(){
        // TODO: 관리자 비밀번호 변경.
    }
}
