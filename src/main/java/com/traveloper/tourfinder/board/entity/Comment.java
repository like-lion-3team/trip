package com.traveloper.tourfinder.board.entity;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String content;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Member member;
}
