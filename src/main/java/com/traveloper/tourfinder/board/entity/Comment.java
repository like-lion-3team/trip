package com.traveloper.tourfinder.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Basic{
    @Column(nullable = false)
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    private Article article;

    public Comment(String content, Article article){
        this.content = content;
        this.article = article;
    }
}
