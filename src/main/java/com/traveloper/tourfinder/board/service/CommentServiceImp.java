package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.board.dto.CommentDto;
import com.traveloper.tourfinder.board.entity.Article;
import com.traveloper.tourfinder.board.entity.Comment;
import com.traveloper.tourfinder.board.repo.ArticleRepository;
import com.traveloper.tourfinder.board.repo.CommentRepository;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final AuthenticationFacade facade;

    // 댓글 작성
    @Override
    public CommentDto createComment(Long articleId, CommentDto commentDto) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // article이 존재하지 않을 시
        if (optionalArticle.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);

        Member currentMember = facade.getCurrentMember();
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .member(currentMember)
                .article(optionalArticle.get())
                .build();
        return CommentDto.fromEntity(commentRepository.save(comment));
    }

    @Override
    public CommentDto updateComment(Long articleId, Long commentId, CommentDto commentDto) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // article이 존재하지 않을 시
        if (optionalArticle.isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);
        }
        Article article = optionalArticle.get();
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        // comment가 존재하지 않는 경우
        if (optionalComment.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_NOT_EXISTS);

        Comment comment = optionalComment.get();
        // comment가 article의 댓글이 아닐 경우
        if (!comment.getArticle().getId().equals(articleId))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_COMMENT_MISMATCH);

        Member currentMember = facade.getCurrentMember();
        // 코멘트의 주인이 현재 접속 유저가 아닐 경우
        if (!comment.getMember().getUuid().equals(currentMember.getUuid()))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_FORBIDDEN);

        comment.setContent(commentDto.getContent());
        return CommentDto.fromEntity(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long articleId, Long commentId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // article이 존재하지 않을 시
        if (optionalArticle.isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);
        }
        Article article = optionalArticle.get();
                    Optional<Comment> optionalComment = commentRepository.findById(commentId);
        // comment가 존재하지 않는 경우
        if (optionalComment.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_NOT_EXISTS);

        Comment comment = optionalComment.get();
        // comment가 article의 댓글이 아닐 경우
        if (!comment.getArticle().getId().equals(articleId))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_COMMENT_MISMATCH);

        Member currentMember = facade.getCurrentMember();
        // 코멘트의 주인이 현재 접속 유저가 아닐 경우
        if (!comment.getMember().getUuid().equals(currentMember.getUuid()))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_FORBIDDEN);

        commentRepository.deleteById(commentId);
    }
}

