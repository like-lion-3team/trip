package com.traveloper.tourfinder.view;

import com.traveloper.tourfinder.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ViewController {
    @Value("${naver.ncp.client-id}")
    private String NCP_CLIENT_ID;

    private final EmailService emailService;

    @RequestMapping("/test")
    public String test() {
        return "sample";
    }

    @GetMapping("/api/v1/courses/course-create")
    public String courseCreate(
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        return "course-create";
    }

    @GetMapping("/api/v1/courses/course-list")
    public String courseList(
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        return "course-list";
    }

    @GetMapping("/api/v1/courses/course-list-find")
    public String courseListFind(
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        return "course-list-find";
    }


    @GetMapping("/api/v1/courses/course-read/{courseId}")
    public String courseRead(
            @PathVariable("courseId")
            Long courseId,
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        model.addAttribute("courseId", courseId);
        return "course-read";
    }

    @GetMapping("/api/v1/courses/course-update/{courseId}")
    public String courseUpdate(
            @PathVariable("courseId")
            Long courseId,
            Model model
    ) {
        model.addAttribute("clientId", NCP_CLIENT_ID);
        model.addAttribute("courseId", courseId);
        return "course-update";
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

    @GetMapping("/board")
    public String board() {
        return "board-articles";
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


    @GetMapping("/board/articles/create")
    public String createArticleView() {
        return "article-create";
    }


    @GetMapping("/board/articles/{articleId}/update")
    public String updateArticleView(
            @PathVariable("articleId")
            Long articleId,
            Model model
    ) {
        model.addAttribute("articleId", articleId);
        return "article-update";
    }

    @GetMapping("/board/articles/{articleId}")
    public String readArticleView(
            @PathVariable("articleId")
            Long articleId,
            Model model
    ) {
        model.addAttribute("articleId", articleId);
        return "article-read";
    }

    @GetMapping("/password-change-email-check")
    public String passwordChangeView(){
        return "password-change-email-check";
    }

    @GetMapping("/password-change")
    public String passwordChangeForm(
            @RequestParam("email")
            String email,
            @RequestParam("code")
            String code
    ){



        System.out.println(email + "email");
        System.out.println(code + "code");


        boolean verify = emailService.verifyCode(email,code);
        if(!verify || email.isEmpty() || code.isEmpty()){
            return "redirect:login";
        }else{
            return "password-change-form";
        }

    }



}
