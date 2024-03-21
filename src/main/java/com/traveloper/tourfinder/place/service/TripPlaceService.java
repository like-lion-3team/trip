package com.traveloper.tourfinder.place.service;

import com.traveloper.tourfinder.place.repo.TripPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripPlaceService {
    private final TripPlaceRepository tripPlaceRepository;
}
