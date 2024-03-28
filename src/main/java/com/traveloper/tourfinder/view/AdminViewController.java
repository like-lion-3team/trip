package com.traveloper.tourfinder.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class AdminViewController {

    @GetMapping("/admin/dashboard")
    public String dashboardMain() {
        return "admin-dashboard";
    }

    @GetMapping("/admin/login")
    public String adminLogin() {return "admin-login";}
}
