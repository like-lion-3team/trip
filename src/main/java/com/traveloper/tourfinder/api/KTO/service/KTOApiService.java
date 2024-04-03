package com.traveloper.tourfinder.api.KTO.service;

import com.traveloper.tourfinder.api.KTO.dto.detail.DetailsCommonDto;
import java.util.HashMap;
import java.util.Map;

import com.traveloper.tourfinder.api.KTO.dto.search.KTOKeywordSearchDto;
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

    // 지역기반 관광정보 조회 (코스 찾기)
    public Object getCourseList(
            Integer pageNo,
            Integer areaCode,
            Integer sigunguCode
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("numOfRows", 10);
        params.put("pageNo", pageNo);
        params.put("serviceKey", serviceKey);
        params.put("MobileOS", "ETC");
        params.put("MobileApp", "AppTest");
        params.put("_type", "json");
        // params.put("contentTypeId", 25);
        params.put("areaCode", areaCode);
        params.put("sigunguCode", sigunguCode);
        return ktoSearchService.AreaBasedList(params);
    }

    // 지역 코드 조회 (areaCode 가 있는 경우)
    public Object getAreaCodeList(
            Integer areaCode
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("numOfRows", 50);
        params.put("serviceKey", serviceKey);
        params.put("MobileOS", "ETC");
        params.put("MobileApp", "AppTest");
        params.put("_type", "json");
        params.put("areaCode", areaCode);
        return ktoSearchService.AreaCodeList(params);
    }
}
