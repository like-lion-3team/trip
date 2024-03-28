package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.board.dto.CommentDto;

import java.util.List;

public interface CommentService {
    Long createComment(CommentDto commentDto, Long articleId);

    void updateComment(CommentDto commentDto, Long commentID);

    void deleteComment(Long commentId);
}
