package com.traveloper.tourfinder.board.controller;

import com.traveloper.tourfinder.board.dto.CommentDto;
import com.traveloper.tourfinder.board.service.CommentServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImp commentService;
    // 댓글 작성 POST /articles/{articleId}/comments
    @PostMapping
    public CommentDto createComment(
            @PathVariable("articleId")
            Long articleId,
            @RequestBody
            CommentDto commentDto

    ) {
        return commentService.createComment(articleId, commentDto);
    }

    @PutMapping("{commentId}")
    public CommentDto updateComment(
            @PathVariable("articleId")
            Long articleId,
            @PathVariable("commentId")
            Long commentId,
            @RequestBody
            CommentDto commentDto
    ) {
        return commentService.updateComment(articleId, commentId, commentDto);
    }

    // 댓글 삭제 DELETE /articles/{articleId}/comments/delete
    @DeleteMapping("/{commentId}/delete")
    public void deleteComment(
            @PathVariable("articleId")
            Long articleId,
            @PathVariable("commentId")
            Long commentId
    ) {
        commentService.deleteComment(articleId, commentId);
    }
}
