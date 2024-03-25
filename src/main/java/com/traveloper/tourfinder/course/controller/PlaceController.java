package com.traveloper.tourfinder.course.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveloper.tourfinder.api.KTO.dto.KTOKeywordSearchDto;
import com.traveloper.tourfinder.api.KTO.dto.detail.DetailsCommonDto;
import com.traveloper.tourfinder.api.KTO.service.KTOApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final KTOApiService ktoApiService;

    // 관광정보 서비스 API 여행지 정보 검색
    @GetMapping("/api-test/search")
    public KTOKeywordSearchDto searchPlaces(
            @RequestParam("keyword")
            String keyword
    ) throws JsonProcessingException {
        return ktoApiService.getTripPlaces(keyword);
    }

    // 관광정보 서비스 API 여행지 정보 검색
    @GetMapping("/api-test/detail")
    public DetailsCommonDto placesDetails(
            @RequestParam("contentId")
            String contentId
    ) {
        return ktoApiService.getPlaceDetails(contentId);
    }
}
