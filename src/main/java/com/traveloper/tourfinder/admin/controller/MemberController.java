package com.traveloper.tourfinder.admin.controller;

import com.traveloper.tourfinder.admin.dto.PeriodDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/admin")
public class MemberController {

    // member?page={page}&size={size}&permission={permission}
    @GetMapping("/member")
    public void getMembers(){
        // TODO: 회원목록 조희
    }

    @GetMapping("/member/{memberUuid}")
    public void getMemberDetail(){
        // TODO: 멤버 상세 정보 조회
    }

    @PatchMapping("/member/{memberUuid}/block")
    public void blockMember(
            @PathVariable("memberUuid")
            String memberUuid,
           @RequestBody
            PeriodDto dto
    ){
        // TODO: 회원 차단
    }

    @PatchMapping("/member/{memberUuid}/unblock")
    public void unBlockMember(
            @PathVariable("memberUuid")
            String memberUuid
    ){
        // TODO: 회원 차단 해제
    }

    @DeleteMapping("/member/{memberUuid}/token/revoke")
    public void revokeToken(
            @PathVariable("memberUuid")
            String memberUuid
    ){
        // TODO: 유저 인증정보 삭제
    }

}
