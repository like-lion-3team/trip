package com.traveloper.tourfinder.admin.controller;

import com.traveloper.tourfinder.admin.dto.PeriodDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    // member?page={page}&size={size}&permission={permission}
    @GetMapping
    public void getMembers(){
        // TODO: 회원목록 조희
    }

    @GetMapping("/{memberUuid}")
    public void getMemberDetail(
            @PathVariable String memberUuid){
        // TODO: 멤버 상세 정보 조회
    }

    @PatchMapping("/{memberUuid}/block")
    public void blockMember(
            @PathVariable("memberUuid")
            String memberUuid,
            @RequestBody
            PeriodDto dto
    ){
        // TODO: 회원 차단
    }

    @PatchMapping("/{memberUuid}/unblock")
    public void unBlockMember(
            @PathVariable("memberUuid")
            String memberUuid
    ){
        // TODO: 회원 차단 해제
    }

    @DeleteMapping("/{memberUuid}/token/revoke")
    public void revokeToken(
            @PathVariable("memberUuid")
            String memberUuid
    ){
        // TODO: 유저 인증정보 삭제
    }

}