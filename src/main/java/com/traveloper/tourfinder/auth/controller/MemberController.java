package com.traveloper.tourfinder.auth.controller;


import com.nimbusds.openid.connect.sdk.assurance.request.VerifiedClaimsSetRequest;
import com.traveloper.tourfinder.auth.dto.CreateMemberDto;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.dto.SignInDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.password.UpdatePasswordReq;
import com.traveloper.tourfinder.auth.service.MemberService;
import com.traveloper.tourfinder.board.dto.ArticleDto;
import com.traveloper.tourfinder.common.util.RandomCodeUtils;
import com.traveloper.tourfinder.common.util.ValidateUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.MemberUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<TokenDto> signIn(
            @RequestBody
            SignInDto dto
    ) {
        log.info("test");
        return ResponseEntity.ok(memberService.login(dto));
    }

    @GetMapping("/sign-out")
    public void signOut(){
        // TODO: 로그아웃 기능
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        return;
    }

    @GetMapping("/my")
    public void getMyInfo(){
        // TODO: 내 정보 ( 마이페이지 )
    }

    @PutMapping("/me/password")
    public void updatePassword(
            @Validated
            @RequestBody
            UpdatePasswordReq updatePasswordReq
    ){
        // TODO: 비밀번호 수정 ( 마이페이지 )
//        String email = 현재 로그인한 회원
//        memberService.updatePassword(
//                email,
//                updatePasswordReq.getCurrentPassword(),
//                updatePasswordReq.getNewPassword());
    }

    @PutMapping("/password-recovery")
    public void recoverPassword(
            @RequestParam String changePw
    ){
        // TODO: 비밀번호 복구 ( 비밀번호 찾기 -> 변경 )
    }

    @PostMapping("/password-recovery")
    public void recoverPasswordRequest() {
        // TODO: 비밀번호 복구 요청 ( 이메일 전송 )
    }

    @PatchMapping("/email")
    public void updateEmail(
            @RequestParam String newEmail
    ) {
        // TODO: 이메일 변경 기능
        // TODO: 이메일로 전송된 코드 검증 후 변경


    }








}



