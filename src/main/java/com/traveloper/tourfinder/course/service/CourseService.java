package com.traveloper.tourfinder.course.service;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import com.traveloper.tourfinder.course.dto.CourseDto;
import com.traveloper.tourfinder.course.entity.Course;
import com.traveloper.tourfinder.course.repo.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final AuthenticationFacade facade;
    public CourseDto addCourse(CourseDto courseDto) {
        Member member = facade.getCurrentMember();
        Course course = Course.builder()
                .title(courseDto.getTitle())
                .desc(courseDto.getDesc())
                .member(member)
                .build();
        return CourseDto.fromEntity( courseRepository.save(course));
    }

    public void getCourse() {

    }

    public void getMyCourse() {

    }

    public void getTargetMemberCoruse() {

    }

    public void getRecommendedCourse() {

    }

    public void getCourseDetail() {

    }

    public void updateCourse() {

    }

    public void deleteCourse() {

    }
}
