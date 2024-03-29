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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final AuthenticationFacade facade;
    private final ArticleTagRepository articleTagRepository;


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

        ArticleDto newDto = new ArticleDto(article.getId(), article.getTitle(),article.getContent());
        createTagList(newDto, newDto);
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
        article.setImagePath(articleDto.getImagePath());

        //  변경 내용 적용된 게시글dto
        ArticleDto newDto = new ArticleDto(articleDto.getId(),articleDto.getTitle(), articleDto.getContent(),articleDto.getTags());
        //  내용 중 태그 내용 변경(인자로 변경 예정 내용, 현재 저장 내용 같이 가져감)
        createTagList(newDto, ArticleDto.fromEntity(optionalArticle));

        return ArticleDto.fromEntity(articleRepository.save(article));
    }


    public void deleteArticle(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        Optional<ArticleTag> ArticleTag = articleTagRepository.findArticleTagByArticleId(articleId);
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
        articleTagRepository.delete(articleTag);

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

    private void createTagList(ArticleDto newDto, ArticleDto originalDto){
        Pattern myPattern = Pattern.compile("#(\\s+)");
        Matcher matcher = myPattern.matcher(newDto.getContent());
        Set<String> tags = new HashSet<>();
        while (matcher.find()){
            tags.add(matcher.group(1));
        }
        saveTag(tags,originalDto);
    }

    private void saveTag(Set<String> newTagSet, ArticleDto originalDto) {
        Article article = articleRepository.findById(originalDto.getId()).orElseThrow();
        Set<ArticleTag> originalTagSet = article.getTags();
        System.out.println("orgiinalTagSet사이즈....." + originalTagSet.size());

        for (String tagContent : newTagSet) {
            Tag result = tagRepository.findTagByContent(tagContent);
            Tag newTag;
            // 미등록 태그 -> 태그 추가

            if (result==null) {
                newTag = new Tag(tagContent);
                tagRepository.save(newTag);
            } else {
                System.out.println("궁금한 태그를 검색하세요"+result.getContent());
                newTag = result;
            }
            ArticleTag newTag = new ArticleTag(article, newTag);
            if (originalTagSet.isEmpty()) {
                articleTagRepository.save(newTag);
            } else {
                if (!(originalTagSet.contains(newTag))) {
                    articleTagRepository.save(new ArticleTag(article, newTag));
                } else {
                    break;
                }
            }
        }
    }
}
