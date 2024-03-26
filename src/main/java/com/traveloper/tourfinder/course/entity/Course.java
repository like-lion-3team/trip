package com.traveloper.tourfinder.course.entity;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder
@RequiredArgsConstructor
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    private String title;
    @Setter
    private String desc;

    @Setter
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Place> places;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
