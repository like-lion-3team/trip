package com.traveloper.tourfinder.place.repo;

import com.traveloper.tourfinder.place.entity.TripPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TripPlaceRepository extends JpaRepository<TripPlace, Long> {
}
