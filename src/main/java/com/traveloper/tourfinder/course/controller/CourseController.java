package com.traveloper.tourfinder.course.controller;


import com.traveloper.tourfinder.course.dto.CourseDto;
import com.traveloper.tourfinder.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Course", description = "여행 코스와 관련된 API")
@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public CourseDto addCourse(
            @RequestBody
            CourseDto courseDto
    ) {
        return courseService.addCourse(courseDto);
    }

    @GetMapping
    public void getCourse(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        courseService.getCourse(pageable);
    }

    @GetMapping("/my-courses")
    public List<CourseDto> getMyCourse() {
        return courseService.getMyCourse();
    }

    @GetMapping("/users/{userId}/courses")
    public List<CourseDto> getTargetMemberCourse(
            @PathVariable("userId")
            String userId
    ) {
        return courseService.getTargetMemberCoruse(userId);
    }

    @GetMapping("/recommended")
    public void getRecommendedCourse(Pageable pageable) {
        // TODO: 추천코스 조회
        courseService.getRecommendedCourse();
    }

    @GetMapping("{courseId}")
    public void getCourseDetail(
            @PathVariable("courseId")
            Integer courseId
    ) {
        // TODO: 코스 상세보기
        // TODO: 코스 아이디 받아서 상세정보 조회
        courseService.getCourseDetail();
    }

    @PutMapping("{courseId}")
    public void updateCourse(
            @PathVariable("courseId")
            Integer courseId
    ) {
        // TODO: 코스 수정하기
        // TODO: 코스 아이디 받아서 코스 수정
        courseService.updateCourse();
    }

    @DeleteMapping("{courseId}")
    public void deleteCourse(
            @PathVariable("courseId")
            Integer courseId
    ) {
        // TODO: 코스 삭제하기
        // TODO: 본인만 삭제 할 수 있음. 본인인지 확인하는 과정 필요
        // TODO: 코스 아이디 받아서 코스 삭제
        courseService.deleteCourse();
    }
}
