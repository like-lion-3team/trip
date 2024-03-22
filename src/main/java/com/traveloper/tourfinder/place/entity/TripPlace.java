package com.traveloper.tourfinder.place.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String desc;
    private String thumbnailUrl;
    private String phone;
    private String address;
    // mapx
    private Double lng;
    // mapy
    private Double lat;
}