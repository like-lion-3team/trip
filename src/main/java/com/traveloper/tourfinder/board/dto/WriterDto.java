package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriterDto {
    private Long memberId;
    private String nickname;

    public static WriterDto fromMember(Member member) {
        return WriterDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
