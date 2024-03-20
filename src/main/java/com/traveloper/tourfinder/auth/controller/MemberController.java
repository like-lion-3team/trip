package com.traveloper.tourfinder.auth.controller;


import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.dto.TokenDto;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
            @RequestParam("email")
            String email,
            @RequestParam("password")
            String password
    ) {
        log.info("test");
        return memberService.login(email, password);
    }
}



