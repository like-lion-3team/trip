package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private List<CommentDto> comments = new ArrayList<>();



    public ArticleDto(Long articleId, String title, String content){
        this.title = title;
        this.content = content;
    }

    public static ArticleDto fromEntity(Article entity) {
        ArticleDto dto = new ArticleDto(articleId, title, content);
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.content = entity.getContent();
        dto.comments = new ArrayList<>();

        return dto;
    }
}
