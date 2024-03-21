package com.traveloper.tourfinder.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("articles")
@RequiredArgsConstructor
public class ArticleController {

    // 게시글 등록
    @PostMapping("/register")
    public String createArticle(
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content
//            @RequestParam(member-id)
//            Long memberId,
//            @RequestParam(board-id)
//            Long boardId
    ) {
        return null;
    }

    // 게시글 수정
    @GetMapping("/{articleId}")
    public String updateArticle(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content
    ) {
        return null;
    }

    // 게시글 삭제
    @PostMapping("/{articleId}/delete")
    public String deleteArticle(
            @PathVariable("articleId")
            Long articleId
    ) {
        return null;
    }




    // 게시글 상세보기 GET /articles/{articleId}
}
