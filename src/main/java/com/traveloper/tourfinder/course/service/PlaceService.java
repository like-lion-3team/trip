package com.traveloper.tourfinder.course.service;

import com.traveloper.tourfinder.course.repo.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
}
