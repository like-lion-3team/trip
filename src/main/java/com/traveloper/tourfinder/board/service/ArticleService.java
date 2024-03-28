package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.board.dto.*;
import com.traveloper.tourfinder.board.entity.*;
import com.traveloper.tourfinder.board.repo.*;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final AuthenticationFacade facade;

    // 게시글 저장 메서드
    public ArticleDto createArticle(ArticleDto articleDto, MultipartFile image) {
        Member currentMember = facade.getCurrentMember();
        Article article = Article.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                //.imagePath() TODO image 저장후 path 저장
                .member(currentMember)
                .build();
        return ArticleDto.fromEntity(articleRepository.save(article));
    }

    // article 페이지 단위로 받아오기
    public List<ArticleDto> readArticlePaged(Pageable pageable) {
        return articleRepository.findAll(pageable).stream()
                .map(ArticleDto::fromEntity)
                .toList();
    }

    // 특정 articleId의 article 가져오기
    public ArticleDto readArticle(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // article이 존재하지 않는 경우
        if (optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Article article = optionalArticle.get();
        return ArticleDto.fromEntity(article);
    }

    // article 수정
    public ArticleDto updateArticle(Long articleId, ArticleDto articleDto) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // 존재하지 않는 article일 경우
        if (optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Article article = optionalArticle.get();
        Member currentMember = facade.getCurrentMember();
        // article의 주인이 아닌 경우
        if (!article.getMember().getUuid().equals(currentMember.getUuid()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // article 수정
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        // TODO 이미지 관련 로직 추가해야함 (이미지 Multipart 파일 저장, 기존 파일 삭제 등)
        article.setImagePath(articleDto.getImagePath());

        return ArticleDto.fromEntity(articleRepository.save(article));
    }


    public void deleteArticle(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // article이 존재하지 않는 경우
        if (optionalArticle.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Article article = optionalArticle.get();
        Member currentMember = facade.getCurrentMember();
        // article의 주인이 아닌 경우
        if (!article.getMember().getUuid().equals(currentMember.getUuid()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // TODO 저장된 image 삭제하는 로직 추가해야함
        articleRepository.delete(article);
    }
}
