package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.board.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long articleId, CommentDto commentDto);

    CommentDto updateComment(Long articleId, Long commentId, CommentDto commentDto);

    void deleteComment(Long articleId, Long commentId);
}
