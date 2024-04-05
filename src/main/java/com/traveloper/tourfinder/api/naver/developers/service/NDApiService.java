package com.traveloper.tourfinder.api.naver.developers.service;

import com.traveloper.tourfinder.api.naver.developers.dto.LocalSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NDApiService {
    private final NDSearchService ndSearchService;

    public LocalSearchDto ndLocalSearch(String query, Integer display, String sort) {
        Map<String, String> params = new HashMap<>();
        params.put("query", query);
        params.put("display", String.valueOf(display));
        params.put("sort", sort);
        return ndSearchService.localSearch(params);
    }
}
