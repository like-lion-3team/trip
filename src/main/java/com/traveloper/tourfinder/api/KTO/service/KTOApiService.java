package com.traveloper.tourfinder.api.KTO.service;

import com.traveloper.tourfinder.api.KTO.dto.detail.DetailsCommonDto;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveloper.tourfinder.api.KTO.dto.KTOKeywordSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KTOApiService {
    private final KTOSearchService ktoSearchService;

    // 일반 인증키 (Encoding)
    @Value("${kto.serviceKey}")
    private String serviceKey;

    // 여행지 키워드 검색
    public KTOKeywordSearchDto getTripPlaces(
            String keyword,
            Integer pageNo
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("numOfRows", 12);
        params.put("pageNo", pageNo);
        params.put("MobileOS", "ETC");
        params.put("MobileApp", "AppTest");
        params.put("_type", "json");
        params.put("serviceKey", serviceKey);
        params.put("listYN", "Y");
        params.put("arrange", "A");
        params.put("keyword", keyword);

        return ktoSearchService.SearchKeyword(params);
    }

    // 여행지 상세 정보 확인
    public DetailsCommonDto getPlaceDetails(
            String contentId
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("serviceKey", serviceKey);
        params.put("MobileOS", "ETC");
        params.put("MobileApp", "AppTest");
        params.put("_type", "json");
        params.put("contentId", contentId);
        params.put("defaultYN", "Y");
        params.put("firstImageYN", "Y");
        params.put("mapinfoYN", "Y");
        params.put("overviewYN", "Y");
        params.put("addrinfoYN", "Y");
        return ktoSearchService.DetailCommon(params);
    }
}
