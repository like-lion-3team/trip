package com.traveloper.tourfinder.course.controller;

import com.traveloper.tourfinder.api.KTO.service.KTOApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TripPlaceController {
    private final KTOApiService ktoApiService;

    // 관광정보 서비스 API 여행지 정보 검색
    @GetMapping("/api-test/search")
    public Object searchPlaces(
            @RequestParam("keyword")
            String keyword
    ) {
        return ktoApiService.getTripPlaces(keyword);
    }

    // 관광정보 서비스 API 여행지 정보 검색
    @GetMapping("/api-test/detail")
    public Object placesDetails(
            @RequestParam("contentId")
            String contentId
    ) {
        return ktoApiService.getPlaceDetails(contentId);
    }
}
