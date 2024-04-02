package com.traveloper.tourfinder.api.KTO.service;

import com.traveloper.tourfinder.api.KTO.dto.detail.DetailsCommonDto;
import java.util.Map;

import com.traveloper.tourfinder.api.KTO.dto.KTOKeywordSearchDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange("/B551011/KorService1/")
public interface KTOSearchService {
    // 키워드 검색
    @GetExchange("/searchKeyword1")
    KTOKeywordSearchDto SearchKeyword(@RequestParam Map<String, Object> params);

    // 공통 정보 조회
    @GetExchange("/detailCommon1")
    DetailsCommonDto DetailCommon(@RequestParam Map<String, String> params);

    // 지역 기반 관광정보 조회 (코스 찾기)
    @GetExchange("/areaBasedList1")
    Object AreaBasedList(@RequestParam Map<String, Object> params);

    // 지역 코드 조회
    @GetExchange("/areaCode1")
    Object AreaCodeList(@RequestParam Map<String, Object> params);
}