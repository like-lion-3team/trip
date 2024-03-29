package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.board.dto.*;
import com.traveloper.tourfinder.board.entity.*;
import com.traveloper.tourfinder.board.repo.*;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import com.traveloper.tourfinder.common.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final AuthenticationFacade facade;

    // 게시글 저장 메서드
    public ArticleDto createArticle(String title, String content, MultipartFile[] images) {
        Member currentMember = facade.getCurrentMember();

        Article article = Article.builder()
                .title(title)
                .content(content)
                //.imagePath() TODO image 저장후 path 저장
                .member(currentMember)
                .build();

        for (MultipartFile image : images) {
            String imagePath = saveImage(image);
            article.getImages().add(imagePath);
        }

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
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);

        Article article = optionalArticle.get();
        return ArticleDto.fromEntity(article);
    }

    // article 수정
    public ArticleDto updateArticle(Long articleId, ArticleDto articleDto) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // 존재하지 않는 article일 경우
        if (optionalArticle.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);

        Article article = optionalArticle.get();
        Member currentMember = facade.getCurrentMember();
        // article의 주인이 아닌 경우
        if (!article.getMember().getUuid().equals(currentMember.getUuid()))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_FORBIDDEN);

        // article 수정
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        // TODO 이미지 관련 로직 추가해야함 (이미지 Multipart 파일 저장, 기존 파일 삭제 등)

        return ArticleDto.fromEntity(articleRepository.save(article));
    }


    public void deleteArticle(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        // article이 존재하지 않는 경우
        if (optionalArticle.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);

        Article article = optionalArticle.get();
        Member currentMember = facade.getCurrentMember();
        // article의 주인이 아닌 경우
        if (!article.getMember().getUuid().equals(currentMember.getUuid()))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_FORBIDDEN);

        // TODO 저장된 image 삭제하는 로직 추가해야함
        articleRepository.delete(article);
    }

    // 게시글 좋아요 처리
    public void toggleArticleLike(Long articleId) {
        // article이 존재하지 않는 경우
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);

        Article article = optionalArticle.get();
        Member currentMember = facade.getCurrentMember();

        Optional<ArticleLike> optionalLike = articleLikeRepository.findByMemberAndArticle(currentMember, article);
        if (optionalLike.isPresent()) {
            articleLikeRepository.delete(optionalLike.get());
        } else {
            ArticleLike newLike = ArticleLike.builder()
                    .member(currentMember)
                    .article(article)
                    .build();
            articleLikeRepository.save(newLike);
        }
    }

    public String saveImage(MultipartFile image) {
        String imgDir = "img/articles/";
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path imgPath = Path.of(imgDir + imgName);

        try {
            Files.createDirectories(Path.of(imgDir));
            image.transferTo(imgPath);
            log.info(image.getName());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return imgPath.toString();
    }
}
