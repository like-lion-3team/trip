package com.traveloper.tourfinder.board.entity;

import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Builder
public class Tag extends Basic {
    @Column(nullable = false, unique = true)
    private String content;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private final Set<ArticleTag> ArticleSet = new HashSet<>();

    public Tag(String Content){
        this.content = content;
    }
}
