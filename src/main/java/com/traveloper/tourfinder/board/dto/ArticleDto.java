package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import com.traveloper.tourfinder.board.entity.ArticleTag;
import com.traveloper.tourfinder.board.entity.Comment;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private WriterDto writer;
    private List<CommentDto> comments;
    private List<TagDto> tags;

    public static ArticleDto fromEntity(Article entity) {
        List<CommentDto> commentDtoList = entity.getComments().stream()
                .map(CommentDto::fromEntity)
                .toList();

        WriterDto writer = WriterDto.builder()
                .memberId(entity.getMember().getId())
                .nickname(entity.getMember().getNickname())
                .build();

        // TODO tag 추가
        return ArticleDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                //.imagePath(entity.getImagePath())
                .writer(writer)
                .comments(commentDtoList)
                //.tags()
                .build();
    }
}
