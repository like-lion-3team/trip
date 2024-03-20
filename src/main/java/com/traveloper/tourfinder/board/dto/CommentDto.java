package com.traveloper.tourfinder.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
}
