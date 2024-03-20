package com.traveloper.tourfinder.course.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Course", description = "여행 코스와 관련된 API")
@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    @PostMapping
    public void addCourse(){
        // TODO: 여행코스 등록
    }

    @GetMapping
    public void getCourse(Pageable pageable){
        // TODO: 여행 코스 조회
    }

    @GetMapping("/my-courses")
    public void getMyCourse(){
        // TODO: 나의 여행코스 조회
        // TODO: 어떤 유저가 보낸 요청인지 식별
    }

    @GetMapping("/users/{userId}/courses")
    public void getTargetMemberCourse(String uuid){
        // TODO: 특정 유저의 코스 조회
        // TODO: 어떤 유저인지 uuid로 식별
    }

    @GetMapping("/recommended")
    public void getRecommendedCourse(Pageable pageable){
        // TODO: 추천코스 조회
    }

    @GetMapping("{courseId}")
    public void getCourseDetail(
            @PathVariable("courseId")
            Integer courseId
    ){
        // TODO: 코스 상세보기
        // TODO: 코스 아이디 받아서 상세정보 조회
    }

    @GetMapping("{courseId}")
    public void updateCourse(
            @PathVariable("courseId")
            Integer courseId
    ){
        // TODO: 코스 수정하기
        // TODO: 코스 아이디 받아서 코스 수정
    }

    @GetMapping("{courseId}")
    public void deleteCourse(
            @PathVariable("courseId")
            Integer courseId
    ){
        // TODO: 코스 삭제하기
        // TODO: 본인만 삭제 할 수 있음. 본인인지 확인하는 과정 필요
        // TODO: 코스 아이디 받아서 코스 삭제
    }
}
