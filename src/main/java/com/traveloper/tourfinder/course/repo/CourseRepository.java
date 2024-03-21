package com.traveloper.tourfinder.course.repo;

import com.traveloper.tourfinder.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository
    extends JpaRepository<Course, Long> {
}
