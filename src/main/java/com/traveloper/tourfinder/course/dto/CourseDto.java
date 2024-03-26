package com.traveloper.tourfinder.course.dto;

import com.traveloper.tourfinder.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private String title;
    private String desc;
    List<PlaceDto> places;
    private Long memberId;

    public static CourseDto fromEntity(Course entity) {
        List<PlaceDto> placeDtoList = entity.getPlaces().stream()
                .map(PlaceDto::fromEntity).toList();

        return CourseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .desc(entity.getDesc())
                .places(placeDtoList)
                .memberId(entity.getMember().getId())
                .build();
    }
}
