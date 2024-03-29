package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.ArticleTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.HashSet;
import java.util.Set;


public class ArticleTagDto {
    private Long id;

    public static ArticleTagDto fromEntity(ArticleTag entity){
        ArticleTagDto dto = new ArticleTagDto();
        dto.id = entity.getId();
        return dto;
    }
}
