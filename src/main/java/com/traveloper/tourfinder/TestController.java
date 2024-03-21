package com.traveloper.tourfinder;

import com.traveloper.tourfinder.api.naver.developers.dto.LocalSearchDto;
import com.traveloper.tourfinder.api.naver.developers.service.NDApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final NDApiService ndApiService;
    @GetMapping("test")
    public Object test() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("api-test/local")
    public LocalSearchDto test(
            @RequestParam("query")
            String query,
            @RequestParam("display")
            Integer display,
            @RequestParam("sort")
            String sort
    ) {
        return ndApiService.ndLocalSearch(query, display, sort);
    }
}
