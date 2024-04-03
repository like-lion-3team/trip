package com.traveloper.tourfinder.admin.controller;

import com.traveloper.tourfinder.admin.dto.PagingMembersDto;
import com.traveloper.tourfinder.admin.dto.PeriodDto;
import com.traveloper.tourfinder.admin.service.AdminMemberService;
import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/admin/members")
@AllArgsConstructor
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    // member?page={page}&size={size}&permission={permission}
    @GetMapping
    public ResponseEntity<PagingMembersDto> getMembers(
            Pageable pageable
    ){

        System.out.println("멤버 조회");
        Page<Member> members = adminMemberService.findMembers(pageable);

        List<MemberDto> memberDtos = members.getContent().stream().map(member ->
                        MemberDto.builder()
                                .id(member.getId())
                                .uuid(member.getUuid())
                                .memberName(member.getMemberName())
                                .nickname(member.getNickname())
                                .email(member.getEmail())
                                .role(member.getRole().getName())
                                .build())
                .collect(Collectors.toList());

        PagingMembersDto pagingMembersDto = PagingMembersDto.builder()
                .members(memberDtos)
                .totalPages(members.getTotalPages())
                .pageSize(members.getSize())
                .pageNumber(members.getNumber())
                .build();

        return ResponseEntity.ok(pagingMembersDto);
    }

    @GetMapping("/{memberUuid}")
    public void getMemberDetail(
            @PathVariable String memberUuid){
        // TODO: 멤버 상세 정보 조회
    }

    @PatchMapping("/{memberUuid}/block")
    public ResponseEntity blockMember(
            @PathVariable("memberUuid")
            String memberUuid,
            @RequestBody
            PeriodDto dto
    ){
        adminMemberService.blockMember(memberUuid);
        return ResponseEntity.ok( "{}");
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
