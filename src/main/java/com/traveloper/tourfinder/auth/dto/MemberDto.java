package com.traveloper.tourfinder.auth.dto;

import com.traveloper.tourfinder.auth.entity.Member;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String uuid;
    private String memberName;
    private String nickname;
    private String email;
    private String role;

    public static MemberDto fromEntity(Member entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .memberName(entity.getMemberName())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .role(entity.getRole().getName())
                .build();

    }
}
