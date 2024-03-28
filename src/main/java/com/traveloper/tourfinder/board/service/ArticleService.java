package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.board.dto.*;
import com.traveloper.tourfinder.board.entity.*;
import com.traveloper.tourfinder.board.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    public static ArticleDto viewArticleDetail(Long id) {
    }

    // 게시글 저장 메서드
    @Transactional
    public void createArticle(ArticleDto ArticleDto) {
        Article Article = new Article(ArticleDto.getTitle(), ArticleDto.getContent(), Board);
        ArticleDto.fromEntity(articleRepository.save(Article));
        ArticleDto newDto = new ArticleDto(Article.getId(), Article.getTitle());
        createTagList(newDto, newDto);
    }

    @Transactional
    public void updateArticle(ArticleDto articleDto) {
        Article OriginalArticle = ArticleRepository.findById(ArticleDto.getId()).orElseThrow();
        ArticleDto newDto = new ArticleDto(ArticleDto.getId(), ArticleDto.getTitle(), ArticleDto.getContent(),
                ArticleDto.getTagSet());
    }


    // 게시글 상세 조회
    public ArticleDto ViewArticleDetail(Long id) {
        Article Article = ArticleRepository.findyById(id).orElseThrow();
        return ArticleDto.fromEntity(Article);
    }


    public void deleteArticle(Long id) {
        Article article = ArticleRepository.findById(id).orElse(null);
        if (article != null) {
            List<ArticleTag> articleTag = ArticleTagRepository.findArtilceTagByArticleId(Article.getId());
            articleRepository.delete(Article);
            tagRepository.deleteAll(ArticleTag);
        }
    }
}
