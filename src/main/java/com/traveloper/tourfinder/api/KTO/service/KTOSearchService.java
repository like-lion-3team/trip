package com.traveloper.tourfinder.api.KTO.service;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface KTOSearchService {
    @GetExchange("/B551011/KorService1/searchKeyword1")
    String localSearch(@RequestParam Map<String, String> params);
}