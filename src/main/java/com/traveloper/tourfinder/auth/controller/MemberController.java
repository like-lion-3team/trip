package com.traveloper.tourfinder.auth.controller;


import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.dto.SignInDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth API")
@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/sign-up")
    public MemberDto signUp(
            @RequestBody
            CreateMemberDto dto
    ) {
        return memberService.signup(dto);
    }

    // 로그인

    @PostMapping("/sign-in")
    public TokenDto signIn(
            @RequestBody
            SignInDto dto
    ) {
        log.info("test");
        return memberService.login(dto);
    }

    @GetMapping("/sign-out")
    public void signOut(){
        // TODO: 로그아웃 기능
    }

    @GetMapping("/my")
    public void getMyInfo(){
        // TODO: 내 정보 ( 마이페이지 )
    }

    @PutMapping("/me/password")
    public void updatePassword(){
        // TODO: 비밀번호 수정 ( 마이페이지 )
    }

    @PutMapping("/password-recovery")
    public void recoverPassword(){
        // TODO: 비밀번호 복구 ( 비밀번호 찾기 -> 변경 )
    }

    @PostMapping("/password-recovery")
    public void recoverPasswordRequest(){
        // TODO: 비밀번호 복구 요청 ( 이메일 전송 )
    }

    @PatchMapping("/email")
    public void updateEmail(){
        // TODO: 이메일 변경 기능
        // TODO: 이메일로 전송된 코드 검증 후 변경
    }








}



