package com.traveloper.tourfinder;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("test")
    public Object test() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
