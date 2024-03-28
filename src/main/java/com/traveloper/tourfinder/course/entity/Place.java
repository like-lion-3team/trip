package com.traveloper.tourfinder.course.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import com.traveloper.tourfinder.course.dto.PlaceDto;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    public static Place fromDto(PlaceDto dto) {
        return Place.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .thumbnailUrl(dto.getThumbnailUrl())
                .address(dto.getAddress())
                .lng(dto.getLng())
                .lat(dto.getLat())
                .build();
    }
}