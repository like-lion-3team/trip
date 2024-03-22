package com.traveloper.tourfinder.course.repo;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.course.entity.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository
    extends JpaRepository<Course, Long> {
    List<Course> findAllByMember(Member member);
}
