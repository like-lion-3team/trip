package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.ArticleTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ArticleTagDto {
    private Long id;

    public static ArticleTagDto fromEntity(ArticleTag entity){
        ArticleTagDto dto = new ArticleTagDto();
        dto.id = entity.getId();
        return dto;
    }
}
