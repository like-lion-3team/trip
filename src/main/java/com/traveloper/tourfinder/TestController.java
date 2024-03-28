package com.traveloper.tourfinder;

import com.traveloper.tourfinder.api.naver.developers.dto.LocalSearchDto;
import com.traveloper.tourfinder.api.naver.developers.service.NDApiService;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api-test")
public class TestController {
    private final NDApiService ndApiService;
    private final AuthenticationFacade facade;

    @GetMapping("jwt-test")
    public Object jwtTest() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("facade")
    public Object facadeTest() {
        return facade.getCurrentMember();
    }


    // 네이버 지역검색 api 테스트
    @GetMapping("local")
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
