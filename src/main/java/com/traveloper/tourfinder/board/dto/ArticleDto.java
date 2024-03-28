package com.traveloper.tourfinder.board.dto;

import com.traveloper.tourfinder.board.entity.Article;
import com.traveloper.tourfinder.board.entity.ArticleTag;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private Long memberId;
    private List<CommentDto> comments = new ArrayList<>();
    private List<TagDto> tags = new ArrayList<>();

    public static ArticleDto fromEntity(Article entity) {
        // TODO CommentDto로 변경해서 articleDto에 포함시키기
        // TODO tag 추가
        return ArticleDto.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .imagePath(entity.getImagePath())
                .memberId(entity.getMember().getId())
                //.comments()
                //.tags()
                .build();
    }
}
