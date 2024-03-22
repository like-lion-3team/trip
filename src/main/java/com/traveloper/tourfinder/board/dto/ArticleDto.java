package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import com.traveloper.tourfinder.board.entity.ArticleTag;
import com.traveloper.tourfinder.board.entity.Comment;
import lombok.*;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

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



    public ArticleDto(String title,String content, String password){
        this.title = title;
        this.content = content;
    }

    public static ArticleDto fromEntity(Article entity) {
        ArticleDto dto = new ArticleDto();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.content = entity.getContent();
        dto.comments = new ArrayList<>();

        return dto;
    }
}
