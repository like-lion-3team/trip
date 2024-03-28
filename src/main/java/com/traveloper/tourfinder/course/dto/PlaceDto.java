package com.traveloper.tourfinder.course.dto;


import com.traveloper.tourfinder.course.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String address;
    private Double lng;
    private Double lat;

    public static PlaceDto fromEntity(Place entity) {
        return PlaceDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .thumbnailUrl(entity.getThumbnailUrl())
                .address(entity.getAddress())
                .lng(entity.getLng())
                .lat(entity.getLat())
                .build();
    }
}
