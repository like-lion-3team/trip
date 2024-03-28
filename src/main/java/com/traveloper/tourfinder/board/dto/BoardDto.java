package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    private final List<ArticleDto> articles = new ArrayList<>();


    public static BoardDto fromEntity(Board entity) {
        BoardDto dto = new BoardDto();
        dto.id = entity.getId();
        for (Article article: entity.getArticles()) {
            dto.articles.add(ArticleDto.fromEntity(article));
        }
        return dto;
    }
}
