package com.traveloper.tourfinder.auth.controller;


import com.traveloper.tourfinder.auth.dto.*;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.password.UpdatePasswordReq;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.service.EmailService;
import com.traveloper.tourfinder.auth.service.MemberService;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import com.traveloper.tourfinder.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * <p></p>
     * */
    @PutMapping("/password-recovery")
    public void recoverPassword(
            @RequestParam
            String changePassword,
            @PathVariable("email")
            String email,
            @PathVariable("code")
            String code
    ){
        // TODO: 비밀번호 복구 ( 비밀번호 찾기 -> 변경 )
        emailService.verifyCode(email, code);
        memberService.updatePassword(null, null, changePassword);
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

}



