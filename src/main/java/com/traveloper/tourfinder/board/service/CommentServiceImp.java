package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.board.dto.CommentDto;
import com.traveloper.tourfinder.board.entity.Comment;
import com.traveloper.tourfinder.board.repo.ArticleRepository;
import com.traveloper.tourfinder.board.repo.CommentRepository;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService{

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final AuthenticationFacade facade;

    // 댓글 작성
    @Override
    public Long createComment(CommentDto commentDto, Long articleId) {
        Member currentMember = facade.getCurrentMember();
        Comment result = Comment.builder()
                .member(currentMember)
                .build();
        Comment save = commentRepository.save(result);
        return result.getId();
    }

    @Override
    public void updateComment(CommentDto commentDto, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다.");
        comment.update(commentDto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }


}

