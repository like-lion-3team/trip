package com.traveloper.tourfinder.board.controller;

import com.traveloper.tourfinder.board.dto.CommentDto;
import com.traveloper.tourfinder.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.AbstractDocument;

@Controller
@RequestMapping("/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    // 댓글 작성 POST /articles/{articleId}/comments
    @PostMapping
    public String createComment(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("content")
            String content,
            RedirectAttributes redirectAttributes
    ) {
        commentService.createCommment(new CommentDto(content),articleId);
        return "redirect:/article/{articleId}";
    }

    // 댓글 삭제 DELETE /articles/{articleId}/comments/delete
    @PostMapping("/{commentId}/delete")
    public String deleteComment(
            @PathVariable("articleId")
            Long articleId,
            @PathVariable("commentId")
            Long commentId,
            RedirectAttributes redirectAttributes
    ) {
        //commentService.deleteComment(commentId);
        redirectAttributes.addAttribute("articleId",articleId);
        return "redirect:/article/{articleId}";
    }
}
