package com.traveloper.tourfinder.course.service;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import com.traveloper.tourfinder.course.dto.CourseDto;
import com.traveloper.tourfinder.course.dto.PlaceDto;
import com.traveloper.tourfinder.course.entity.Course;
import com.traveloper.tourfinder.course.entity.Place;
import com.traveloper.tourfinder.course.repo.CourseRepository;
import com.traveloper.tourfinder.course.repo.PlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final AuthenticationFacade facade;

    @Transactional
    public void addCourse(CourseDto courseDto) {
        // Member member = facade.getCurrentMember();

        Course course = Course.builder()
                .title(courseDto.getTitle())
                .desc(courseDto.getDesc())
           //     .member(member)
                .build();
        course = courseRepository.save(course);

        List<PlaceDto> placeDtos = courseDto.getPlaces();
        List<Place> placeList = placeDtos.stream().map(Place::fromDto).toList();
        for (Place place : placeList) {
            place.setCourse(course);
        }

        placeRepository.saveAll(placeList);
    }

    public List<CourseDto> getCourse(Pageable pageable) {
        return courseRepository.findAll(pageable).stream()
                .map(CourseDto::fromEntity)
                .toList();
    }

    public List<CourseDto> getMyCourse() {
        Member member = facade.getCurrentMember();
        return courseRepository.findAllByMember(member).stream()
                .map(CourseDto::fromEntity)
                .toList();
    }

    public List<CourseDto> getTargetMemberCourse(String userId) {
        // TODO userId 라는것이 UUID 인지 논의 필요
        Optional<Member> optionalMember = memberRepository.findMemberByUuid(userId);
        // 없는 유저일 경우
        if (optionalMember.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Member member = optionalMember.get();

        return courseRepository.findAllByMember(member).stream()
                .map(CourseDto::fromEntity)
                .toList();
    }

    public void getRecommendedCourse() {
        // TODO 유저별 추천 코스 ? 구현 여부는 차후 논의
    }

    public CourseDto getOneCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        // 없는 코스일 경우
        if (optionalCourse.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COURSE_NOT_EXISTS);
        return CourseDto.fromEntity(optionalCourse.get());
    }

    @Transactional
    public void updateCourse(Long courseId, CourseDto courseDto) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        // 없는 코스일 경우
        if (optionalCourse.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COURSE_NOT_EXISTS);

        Course course = optionalCourse.get();
        Member member = facade.getCurrentMember();
        // 코스의 주인이 현재 접속 멤버와 다르다면
        if (!course.getMember().getUuid().equals(member.getUuid()))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COURSE_FORBIDDEN);

        // course 엔티티 수정 및 저장
        course.setTitle(courseDto.getTitle());
        course.setDesc(courseDto.getDesc());
        courseRepository.save(course);

        // 이전 Place들 전부 삭제
        placeRepository.deleteAll(course.getPlaces());

        // 새로운 Place들 저장
        List<Place> newPlaces = courseDto.getPlaces().stream()
                .map(Place::fromDto).toList();
        for (Place place : newPlaces) {
            place.setCourse(course);
        }
        placeRepository.saveAll(newPlaces);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        // 없는 코스일 경우
        if (optionalCourse.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COURSE_NOT_EXISTS);

        Course course = optionalCourse.get();
        Member member = facade.getCurrentMember();
        // 코스의 주인이 현재 접속 멤버와 다르다면
        if (!course.getMember().getUuid().equals(member.getUuid()))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COURSE_FORBIDDEN);

        // 연관된 Place들도 전부 삭제
        placeRepository.deleteAll(course.getPlaces());
        courseRepository.deleteById(courseId);
    }
}
