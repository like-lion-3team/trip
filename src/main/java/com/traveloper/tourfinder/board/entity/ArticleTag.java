package com.traveloper.tourfinder.board.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch= FetchType.LAZY)
    private Tag tag;

    public static ArticleTag fromEntity(ArticleTag map) {
        return null;
    }
}
