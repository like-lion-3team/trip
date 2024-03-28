package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import com.traveloper.tourfinder.board.entity.ArticleTag;
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
    @Setter
    private Set<ArticleTag> tagSet = new HashSet<>();


    public ArticleDto(String id, String title, String content){
        this.title = title;
        this.content = content;
    }

    public ArticleDto(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public ArticleDto(Long id, String title, String content, Set<ArticleTag> tagSet){
        this.id = id;
        this.title = title;
        this.content = content;
        this.tagSet = tagSet;
    }

    public static ArticleDto fromEntity(Article entity) {
        ArticleDto dto = new ArticleDto();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.content = entity.getContent();
        dto.comments = new ArrayList<>();

        dto.tagSet = new HashSet<>();
        for (ArticleTag map : entity.getTagSet()){
            dto.tagSet.add(ArticleTag.fromEntity(map));
        }
        return dto;
    }
}
