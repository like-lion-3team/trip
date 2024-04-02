package com.traveloper.tourfinder.auth.controller;


import com.traveloper.tourfinder.auth.dto.*;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.password.UpdatePasswordReq;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.service.EmailService;
import com.traveloper.tourfinder.auth.service.MemberService;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import com.traveloper.tourfinder.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Auth", description = "Auth API")
@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AuthenticationFacade authenticationFacade;
    private final CourseService courseService;
    private final EmailService emailService;
    private final MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto>  signUp(
            @RequestBody
            CreateMemberDto dto
    ) {
        return ResponseEntity.ok(memberService.signup(dto));
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
    public ResponseEntity signOut(
            @RequestHeader("Authorization")
            String accessToken
    ) {
        // TODO: 로그아웃 기능
        memberService.signOut(accessToken);
        return ResponseEntity.ok("");
    }

    @GetMapping("/my")
    public ResponseEntity<MyPageDto> getMyInfo() {
        return ResponseEntity.ok(MyPageDto.builder()
                .member(memberService.findMember(authenticationFacade.getCurrentMember().getUuid()))
                .courseList(courseService.getMyCourse())
                .build());
    }


    @PatchMapping("/me/password")
    public void updatePassword(
            @Validated
            @RequestBody
            UpdatePasswordReq updatePasswordReq,
            Member member
    ){
        // TODO: 비밀번호 수정 ( 마이페이지 )
        memberService.updatePassword(
                String.valueOf(member.getEmail()),
                updatePasswordReq.getCurrentPassword(),
                updatePasswordReq.getNewPassword());

    }

    /**
     * <p>비밀번호 변경시 메일로 전송된 코드 검증 메서드</p>
     * */
    @PostMapping("/password-recovery/verify-code")
    public void verifyCode(
            @RequestBody
            PasswordRecoveryVerifyCodeRequestDto dto,
            HttpServletResponse servletResponse

    ) throws IOException {
        boolean isVerify = emailService.verifyCode(dto.getEmail(), dto.getCode());
        if(!isVerify) throw new GlobalExceptionHandler(CustomGlobalErrorCode.PASSWORD_RECOVERY_CODE_MISS_MATCH);

        // 비밀번호 변경 화면으로 리다이렉트
        // 아래 location은 임시로 넣어 둔 값입니다.
        // 실제 사용할 값으로 변경 해주세요
        servletResponse.sendRedirect("/password-change");
    }

    /**
     * <p>이메일 인증코드, 이메일을 통한 비밀번호 변경 메서드</p>
     * */
    @PutMapping("/password-recovery")
    public ResponseEntity recoverPassword(
            @RequestBody
            PasswordRecoveryRequestDto dto
    ){

        memberService.updatePassword(dto.getEmail(),dto.getCode(), dto.getNewPassword());
        return ResponseEntity.ok("");
    }

    /**
     * <p>비밀번호 복구에 사용할 본인 인증 코드를 이메일로 보냅니다.</p>
     * */
    @PostMapping("/password-recovery")
    public void recoverPasswordRequest(
            @RequestBody
            PasswordRecoveryMailRequestDto dto
    ) {
        emailService.sendVerifyCodeMail(dto.getEmail());
    }

    /**
     * <p>현재 로그인한 멤버의 Id를 반환합니다.</p>
     */
    @GetMapping("/current-member")
    public ResponseEntity<MemberIdDto> getCurrentMemberId() {
        return ResponseEntity.ok(memberService.getCurrentMemberId());
    }

}



