package com.traveloper.tourfinder.course.dto;

import com.traveloper.tourfinder.course.entity.Course;
import com.traveloper.tourfinder.place.entity.TripPlace;
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
    // TODO TripPlace가 아닌 PlaceDto로 변경해야함
    List<TripPlace> places;
    private Long memberId;

    public static CourseDto fromEntity(Course entity) {
        return CourseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .desc(entity.getDesc())
                .places(entity.getPlaces())
                .memberId(entity.getMember().getId())
                .build();
    }
}
