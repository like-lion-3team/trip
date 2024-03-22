package com.traveloper.tourfinder.course.service;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import com.traveloper.tourfinder.course.dto.CourseDto;
import com.traveloper.tourfinder.course.entity.Course;
import com.traveloper.tourfinder.course.repo.CourseRepository;
import com.traveloper.tourfinder.place.repo.TripPlaceRepository;
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
    private final TripPlaceRepository tripPlaceRepository;
    private final AuthenticationFacade facade;
    public CourseDto addCourse(CourseDto courseDto) {
        Member member = facade.getCurrentMember();
        // TODO 여행지 정보도 여기서 저장해야할지 결정해야 함
        Course course = Course.builder()
                .title(courseDto.getTitle())
                .desc(courseDto.getDesc())
                .member(member)
                .build();
        return CourseDto.fromEntity(courseRepository.save(course));
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

    public List<CourseDto> getTargetMemberCoruse(String userId) {
        Optional<Member> optionalMember = memberRepository.findMemberByUuid(userId);
        if (optionalMember.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Member member = optionalMember.get();

        return courseRepository.findAllByMember(member).stream()
                .map(CourseDto::fromEntity)
                .toList();
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
