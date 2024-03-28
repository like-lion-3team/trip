package com.traveloper.tourfinder.board.controller;

import com.traveloper.tourfinder.board.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.traveloper.tourfinder.board.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    // 게시글 등록
    @PostMapping("/register")
    public String createArticle(
            @
    ) {
        articleService.createArticle(new ArticleDto(articleId, title,content));
        return "redirect:/boards";
    }

    // 게시글 수정
    @GetMapping("/{articleId}/update")
    public String updateArticle(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content
    ) {
        articleService.updateArticle(new ArticleDto(articleId,title,content));
        return "redirect:/Article/{articleId}";
    }

    // 게시글 삭제
    @PostMapping("/{articleId}/delete")
    public String deleteArticle(
            @PathVariable("articleId")
            Long articleId
    ) {
        articleService.deleteArticle(articleId);
        return "redirect:/boards";
    }




    // 게시글 상세보기 GET /articles/{articleId}
    @GetMapping("{articleId}")
    public String viewArticleDetail(@PathVariable("articleId") Long id, Model model){
        ArticleDto article = ArticleService.viewArticleDetail(id);
        model.addAttribute("Article",article);
        model.addAttribute("Board",BoardService.findBoardByarticleId(id));
        return "ArticleDetail";
    }
}
