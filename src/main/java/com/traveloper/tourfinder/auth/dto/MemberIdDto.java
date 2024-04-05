package com.traveloper.tourfinder.auth.dto;

import com.traveloper.tourfinder.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberIdDto {
    private Long memberId;

    public static MemberIdDto fromMember(Member member) {
        return new MemberIdDto(member.getId());
    }
}
