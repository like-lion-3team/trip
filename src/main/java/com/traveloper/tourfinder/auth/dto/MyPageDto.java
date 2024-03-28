package com.traveloper.tourfinder.auth.dto;

import com.traveloper.tourfinder.course.dto.CourseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyPageDto {
    MemberDto member;
    List<CourseDto> courseList;
}
