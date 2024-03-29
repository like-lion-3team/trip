package com.traveloper.tourfinder.board.dto;

import lombok.*;
import com.traveloper.tourfinder.board.entity.Comment;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @Setter
    private String content;
    private Long memberId;

    public static CommentDto fromEntity(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .memberId(entity.getMember().getId())
                .build();
    }
}
