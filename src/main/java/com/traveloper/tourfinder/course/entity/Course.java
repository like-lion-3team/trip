package com.traveloper.tourfinder.course.entity;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    private String title;
    private String desc;

    @OneToMany(mappedBy = "places", fetch = FetchType.LAZY)
    private List<CoursePlace> places;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
