package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.board.dto.*;
import com.traveloper.tourfinder.board.entity.*;
import com.traveloper.tourfinder.board.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public static ArticleDto viewArticleDetail(Long id) {
    }

    // 게시글 저장 메서드
    @Transactional
    public void createArticle(ArticleDto ArticleDto){
        Article Article = new Article(ArticleDto.getTitle(), ArticleDto.getTitle(), ArticleDto.getContent(),Board);
        ArticleDto.fromEntity(ArticleRepository.save(Article));
        ArticleDto new Dto = new ArticleDto(articleId, Article.getId(), Article.getTitle());
        createTagList(newDto, newDto);
    }

    // 게시글 수정
    @Transactional
    puvlic void UpdateArticle(ArticleDto ArticleDto){
        Article OriginalAritlce = ArticleRepository.findById(ArticleDto.getId()).orElseThrow();
        ArticleDto newDto = new ArticleDto(ArticleDto.getId(), ArticleDto.getTitle(), ArticleDto.getContent(),
                ArticleDto.getTagSet());
    }

    // 게시글 상세 조회
    public ArticleDto ViewArticleDetail(Long id){
        Article Article = ArticleRepository.findyById(id).orElseThrow();
        return ArticleDto.fromEntity(Article);
}

    public void updateArticle(ArticleDto articleDto) {
    }

    public void deleteArticle(Long articleId) {
    }
