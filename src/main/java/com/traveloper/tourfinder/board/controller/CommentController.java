package com.traveloper.tourfinder.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    // 댓글 작성 POST /articles/{articleId}/comments
    @PostMapping
    public String createComment(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("content")
            String content
    ) {
        return null;
    }

    // 댓글 삭제 DELETE /articles/{articleId}/comments/delete
    @PostMapping("/delete")
    public String deleteComment(
            @PathVariable("articleId")
            Long articleId
    ) {
        return null;
    }


}
