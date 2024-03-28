package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

//entity -> dto
@Getter
@Setter
@NoArgsConstructor
public class ArtilceAllDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;

    public static ArtilceAllDto fromEntity(Article entity){
        ArtilceAllDto dto = new ArtilceAllDto();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.content = entity.getContent();

        return dto;
    }
}
