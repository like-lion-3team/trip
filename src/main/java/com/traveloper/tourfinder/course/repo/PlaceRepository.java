package com.traveloper.tourfinder.course.repo;

import com.traveloper.tourfinder.course.entity.Place;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByContentId(Long contentId);
}
