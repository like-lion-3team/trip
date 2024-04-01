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
    private List<String> images;
    private List<CommentDto> comments;
    private List<String> tags;

    public static ArticleDto fromEntity(Article entity) {
        List<CommentDto> commentDtoList = entity.getComments().stream()
                .map(CommentDto::fromEntity)
                .toList();

        return ArticleDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .images(entity.getImages())
                .writer(WriterDto.fromMember(entity.getMember()))
                .comments(commentDtoList)
                .tags(entity.getTags())
                .build();
    }
}
