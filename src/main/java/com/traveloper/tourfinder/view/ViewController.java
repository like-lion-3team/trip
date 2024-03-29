package com.traveloper.tourfinder.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @Value("${naver.ncp.client-id}")
    private String NCP_CLIENT_ID;

    @RequestMapping("/test")
    public String test() {
        return "sample";
    }

    // 지도 띄우는 테스트
    @GetMapping("api-test/sample-map")
    public String sampleMap(
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        return "sample-map";
    }

    @GetMapping("api-test/sample-course-create")
    public String sampleCourseCreate(
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        return "sample-course-create";
    }

    @GetMapping("api-test/sample-course-list")
    public String sampleCourseList(
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        return "sample-course-list";
    }

    @GetMapping("api-test/sample-course-read/{courseId}")
    public String sampleCourseRead(
            @PathVariable("courseId")
            Long courseId,
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        model.addAttribute("courseId", courseId);
        return "sample-course-read";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "signUp";
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * <p> 소셜 로그인 성공시 서버측으로 로그인 허가 요청을 보내기 위한 callback 페이지 입니다.</p>
     * */
    @GetMapping("/oauth2/callback")
    public String oauthRedirect()
        {return "oauth2-redirect"; }

}
