package com.traveloper.tourfinder.board.entity;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.*;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
    @Setter
    private String content;
    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final List<ArticleLike> likes = new ArrayList<>();

    @ElementCollection
    private final List<String> images = new ArrayList<>();

    @ElementCollection
    private final List<String> tags = new ArrayList<>();
}
