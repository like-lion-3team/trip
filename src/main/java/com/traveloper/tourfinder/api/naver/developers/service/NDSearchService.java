package com.traveloper.tourfinder.api.naver.developers.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

public interface NDSearchService {
    @GetExchange("/v1/search/local")
    Object localSearch(@RequestParam Map<String, String> params);
}
