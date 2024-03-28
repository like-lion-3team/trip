package com.traveloper.tourfinder.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.traveloper.tourfinder.board.entity.Comment;


@Getter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @Setter
    private String content;

    public CommentDto(String content){
        this.content = content;
    }

    public static CommentDto fromEntity(Comment entity){
        CommentDto dto = new CommentDto();
        dto.id = entity.getId();
        dto.content = entity.getContent();
        return dto;
    }
}
