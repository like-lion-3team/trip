package com.traveloper.tourfinder.course.dto;


import com.traveloper.tourfinder.course.entity.CoursePlace;
import com.traveloper.tourfinder.course.entity.Place;
import java.util.List;
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
    private String address;
    private String contentId;
    private String contentTypeId;
    private String thumbnailUrl;
    private Double lng;
    private Double lat;
    private String title;
    List<CoursePlace> courses;

    public static PlaceDto fromEntity(Place entity) {
        return PlaceDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .contentId(entity.getContentid())
                .contentTypeId(entity.getContenttypeid())
                .thumbnailUrl(entity.getThumbnailUrl())
                .lng(entity.getLng())
                .lat(entity.getLat())
                .courses(entity.getCourses())
                .build();
    }

}