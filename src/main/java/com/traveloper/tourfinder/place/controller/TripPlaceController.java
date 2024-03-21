package com.traveloper.tourfinder.place.controller;

import com.traveloper.tourfinder.place.service.TripPlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/apis.data.go.kr/B551011/KorService1")
@RequiredArgsConstructor
public class TripPlaceController {
    private final TripPlaceService tripPlaceService;

    // 관광정보 서비스 API 여행지 정보 검색
    @GetMapping("/searchKeyword1")
    public void searchPlaces(

    ) {

    }

}
