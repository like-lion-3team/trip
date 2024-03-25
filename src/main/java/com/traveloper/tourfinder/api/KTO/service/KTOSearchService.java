package com.traveloper.tourfinder.api.KTO.service;

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
    Object DetailCommon(@RequestParam Map<String, String> params);
}