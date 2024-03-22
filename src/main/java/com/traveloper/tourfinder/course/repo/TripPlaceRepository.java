package com.traveloper.tourfinder.course.repo;

import com.traveloper.tourfinder.course.entity.TripPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPlaceRepository extends JpaRepository<TripPlace, Long> {
}
