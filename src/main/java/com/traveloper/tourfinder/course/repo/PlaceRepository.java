package com.traveloper.tourfinder.course.repo;

import com.traveloper.tourfinder.course.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
