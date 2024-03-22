package com.traveloper.tourfinder.course.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import com.traveloper.tourfinder.course.entity.Course;
import jakarta.persistence.*;
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
public class TripPlace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    // mapx
    private Double lng;
    // mapy
    private Double lat;
}