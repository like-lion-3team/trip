package com.traveloper.tourfinder.api.KTO.service;


import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KTOApiService {
    private final KTOSearchService ktoSearchService;

    // 일반 인증키 (Encoding)
    @Value("${kto.serviceKey}")
    private String serviceKey;

    public Object getTripPlaces(
            String keyword
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("MobileOS", "ETC");
        params.put("MobileApp", "AppTest");
        params.put("_type", "json");
        params.put("arrange", "A");
        params.put("keyword", keyword);
        params.put("serviceKey", serviceKey);
        return ktoSearchService.localSearch(params);
    }
}