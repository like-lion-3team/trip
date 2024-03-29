package com.traveloper.tourfinder.board.controller;

import com.traveloper.tourfinder.board.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.traveloper.tourfinder.board.service.ArticleService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    // 게시글 등록
    @PostMapping
    public ArticleDto createArticle(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart("images") MultipartFile[] images,
            @RequestPart("tags") String tags
    ) {
        return articleService.createArticle(title, content, images, tags);
    }

    @GetMapping
    public List<ArticleDto> readArticlePaged(
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "size", defaultValue = "10")
            Integer size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleService.readArticlePaged(pageable);
    }

    @GetMapping("{articleId}")
    public ArticleDto readArticle(
            @PathVariable("articleId")
            Long articleId
    ) {
        return articleService.readArticle(articleId);
    }

    // 게시글 수정
    @PutMapping("{articleId}")
    public ArticleDto updateArticle(
            @PathVariable("articleId") Long articleId,
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart("images") MultipartFile[] images,
            @RequestPart("tags") String tags
    ) {
        return articleService.updateArticle(articleId, title, content, images, tags);
    }

    // 게시글 삭제
    @DeleteMapping("{articleId}")
    public void deleteArticle(
            @PathVariable("articleId")
            Long articleId
    ) {
        articleService.deleteArticle(articleId);
    }

    // 좋아요 처리
    @PostMapping("{articleId}/like")
    public void toggleArticleLike(
            @PathVariable("articleId")
            Long articleId
    ) {
        articleService.toggleArticleLike(articleId);
    }
}
