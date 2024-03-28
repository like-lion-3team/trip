package com.traveloper.tourfinder.board.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Setter
    @ManyToOne
    private Article article;

    public Comment(String content, Article article){
        this.content = content;
        this.article = article;
    }
}
