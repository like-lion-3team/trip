package com.traveloper.tourfinder.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminViewController {

    @GetMapping
    public String dashboardMain() {
        return "admin-dashboard";
    }
}
