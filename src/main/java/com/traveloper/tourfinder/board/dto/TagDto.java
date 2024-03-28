package com.traveloper.tourfinder.board.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Setter;
import com.traveloper.tourfinder.board.entity.ArticleTag;
import com.traveloper.tourfinder.board.entity.Tag;

public class TagDto {
    private Long id;
    private String content;
    @Setter
    private Set<ArticleTagDto> articleSet = new HashSet<>();

    public static TagDto fromEntity(Tag entity){
        TagDto dto = new TagDto();
        dto.id = entity.getId();
        for (ArticleTag map : entity.getArticleSet()){
            dto.articleSet.add(ArticleTagDto.fromEntity(map));
        }
        return dto;
    }
}
