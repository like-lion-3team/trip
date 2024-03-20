package com.traveloper.tourfinder.place.repo;

import com.traveloper.tourfinder.place.entity.TripPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPlaceRepository extends JpaRepository<Long, TripPlace> {
}
