package com.traveloper.tourfinder.auth.controller;


import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
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
    @GetMapping("/sign-in")
    public void signIn(
            String email,
            String password
    ) {
        memberService.login(email, password);
    }


}



