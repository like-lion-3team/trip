package com.traveloper.tourfinder.auth.controller;


import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/sign-up")
    public void signUp(
            String nickname,
            String email,
            String password
    ) {
        memberService.signup(nickname, email, password);
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



