package com.traveloper.tourfinder.api.KTO.service;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface KTOSearchService {
    // 키워드 검색
    @GetExchange("/B551011/KorService1/searchKeyword1")
    Object SearchKeyword(@RequestParam Map<String, Object> params);

    // 공통 정보 조회
    @GetExchange("/B551011/KorService1/detailCommon1")
    Object DetailCommon(@RequestParam Map<String, String> params);
}