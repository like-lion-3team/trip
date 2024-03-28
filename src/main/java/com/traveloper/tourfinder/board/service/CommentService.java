package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.board.dto.CommentDto;
import com.traveloper.tourfinder.board.entity.Article;
import com.traveloper.tourfinder.board.entity.Comment;
import com.traveloper.tourfinder.board.repo.ArticleRepository;
import com.traveloper.tourfinder.board.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public static void createCommment(CommentDto commentDto, Long articleId) {
        Article article = ArticleRepository.findById(id).orElseThrow();
        Comment comment = new Comment(commentDto.getContent(), commentDto.getPassword(), article);
        CommentDto.fromEntity(CommentRepository.save(comment));
    }
}
