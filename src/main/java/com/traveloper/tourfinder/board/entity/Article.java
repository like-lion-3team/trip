package com.traveloper.tourfinder.board.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;


import java.util.Objects;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor


public class Article {
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
}
