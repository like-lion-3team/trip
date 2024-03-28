package com.traveloper.tourfinder.board.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Objects;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor
public class Article extends Basic{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
    @Setter
    private String content;

    @ManyToOne
    @Setter
    private Board board;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleTag> tagSet = new HashSet<>();

    @Builder
    public Article(String title, String content, String password, Board board) {
        this.title = title;
        this.content = content;
    }
}
