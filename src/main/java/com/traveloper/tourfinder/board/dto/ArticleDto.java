package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import lombok.*;

import java.util.List;

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
    private List<String> tags;

    public static ArticleDto fromEntity(Article entity) {
        List<CommentDto> commentDtoList = entity.getComments().stream()
                .map(CommentDto::fromEntity)
                .toList();

        WriterDto writer = WriterDto.builder()
                .memberId(entity.getMember().getId())
                .nickname(entity.getMember().getNickname())
                .build();

        return ArticleDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                //.imagePath(entity.getImagePath())
                .writer(writer)
                .comments(commentDtoList)
                .tags(entity.getTags())
                .build();
    }
}
