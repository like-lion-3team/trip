package com.traveloper.tourfinder.api.naver.developers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NDApiService {
    private final NDSearchService ndSearchService;

    public Object ndLocalSearch(String query) {
        Map<String, String> params = new HashMap<>();
        params.put("query", query);
        params.put("display", "5");
        params.put("sort", "random");
        return ndSearchService.localSearch(params);
    }
}
