package com.traveloper.tourfinder.course.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// trip_place_ad_id 는 일단 제외

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Place extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String address;
    // mapx
    private Double lng;
    // mapy
    private Double lat;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<CoursePlace> courses;
}