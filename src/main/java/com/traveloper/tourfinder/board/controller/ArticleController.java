package com.traveloper.tourfinder.board.controller;

import com.traveloper.tourfinder.board.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.traveloper.tourfinder.board.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            MultipartFile image,
            @RequestBody
            ArticleDto articleDto
    ) {
        return articleService.createArticle(articleDto, image);
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
            @PathVariable("articleId")
            Long articleId,
            @RequestBody
            ArticleDto articleDto
    ) {
        return articleService.updateArticle(articleId, articleDto);
    }

    // 게시글 삭제
    @DeleteMapping("{articleId}")
    public void deleteArticle(
            @PathVariable("articleId")
            Long articleId
    ) {
        articleService.deleteArticle(articleId);
    }
}
