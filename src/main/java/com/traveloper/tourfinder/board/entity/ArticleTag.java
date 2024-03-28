package com.traveloper.tourfinder.board.entity;

import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@Getter

public class ArticleTag extends Basic{
    @ManyToOne(fetch = FetchType.LAZY)
    private Article Article;

    @ManyToOne(fetch= FetchType.LAZY)
    private Tag Tag;

    @Builder
    public ArticleTag(Article article, Tag tag){
        this.Article = article;
        this.Tag = tag;
    }

    public static ArticleTag fromEntity(ArticleTag map) {
        return null;
    }
}
