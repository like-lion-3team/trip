package com.traveloper.tourfinder.course.service;

import com.traveloper.tourfinder.api.KTO.dto.detail.DetailsItemDto;
import com.traveloper.tourfinder.course.dto.PlaceDto;
import com.traveloper.tourfinder.course.entity.Place;
import com.traveloper.tourfinder.course.repo.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceDto savePlaces(DetailsItemDto detailsCommonDto) {
        Place place = Place.builder()
                .title(detailsCommonDto.getTitle())
                .thumbnailUrl(detailsCommonDto.getFirstimage())
                .address(detailsCommonDto.getAddr1() + " " + detailsCommonDto.getAddr2())
                .lng(Double.valueOf(detailsCommonDto.getMapx()))
                .lat(Double.valueOf(detailsCommonDto.getMapy()))
                .build();
        return PlaceDto.fromEntity(placeRepository.save(place));
    }
}
