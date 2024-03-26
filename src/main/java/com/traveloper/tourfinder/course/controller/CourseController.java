package com.traveloper.tourfinder.course.controller;


import com.traveloper.tourfinder.course.dto.CourseDto;
import com.traveloper.tourfinder.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public List<CourseDto> getCourse(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return courseService.getCourse(pageable);
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
        return courseService.getTargetMemberCourse(userId);
    }

    @GetMapping("/recommended")
    public void getRecommendedCourse(Pageable pageable) {
        // TODO: 추천코스 조회
        courseService.getRecommendedCourse();
    }

    @GetMapping("{courseId}")
    public CourseDto getOneCourse(
            @PathVariable("courseId")
            Long courseId
    ) {
        return courseService.getOneCourse(courseId);
    }

    @PutMapping("{courseId}")
    public void updateCourse(
            @PathVariable("courseId")
            Long courseId,
            @RequestBody
            CourseDto courseDto
    ) {
        courseService.updateCourse(courseId, courseDto);
    }

    @DeleteMapping("{courseId}")
    public void deleteCourse(
            @PathVariable("courseId")
            Long courseId
    ) {
        courseService.deleteCourse(courseId);
    }
}
