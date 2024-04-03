package com.traveloper.tourfinder.course.controller;

import com.traveloper.tourfinder.api.KTO.dto.search.KTOKeywordSearchDto;
import com.traveloper.tourfinder.api.KTO.dto.detail.DetailsCommonDto;
import com.traveloper.tourfinder.api.KTO.service.KTOApiService;
import com.traveloper.tourfinder.course.service.PlaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Place", description = "여행지와 관련된 API")
@RestController
@RequestMapping("api/v1/places")
@RequiredArgsConstructor
public class PlaceController {
    private final KTOApiService ktoApiService;
    private final PlaceService placeService;

    // 관광정보 서비스 API 여행지 정보 검색
    @GetMapping("/search")
    public KTOKeywordSearchDto searchPlaces(
            @RequestParam("keyword")
            String keyword,
            @RequestParam("pageNo")
            Integer pageNo

    ) {
        return ktoApiService.getTripPlaces(keyword, pageNo);
    }

    // 관광정보 서비스 API 여행지 정보 검색
    @GetMapping("/detail")
    public DetailsCommonDto placesDetails(
            @RequestParam("contentId")
            String contentId
    ) {
        return ktoApiService.getPlaceDetails(contentId);
    }


    // 지역기반 관광정보 조회 API (코스 정보만 가져오기)
    @GetMapping("/course-list")
    public Object readCourses(
            @RequestParam("pageNo")
            Integer pageNo,
            @RequestParam("areaCode")
            Integer areaCode,
            @RequestParam("sigunguCode")
            Integer sigunguCode,
            @RequestParam("contentTypeId")
            Integer contentTypeId
    ) {
        return ktoApiService.getCourseList(pageNo, areaCode, sigunguCode, contentTypeId);
    }

    @GetMapping("/area-code")
    public Object readCourses(
            @RequestParam(value = "areaCode", required = false)
            Integer areaCode
    ) {
        if (areaCode == null) {
            return ktoApiService.getAreaCodeList(null);
        } else {
            return ktoApiService.getAreaCodeList(areaCode);
        }
    }
}
