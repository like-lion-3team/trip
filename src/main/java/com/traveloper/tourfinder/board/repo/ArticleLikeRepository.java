package com.traveloper.tourfinder.board.repo;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.board.entity.Article;
import com.traveloper.tourfinder.board.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Optional<ArticleLike> findByMemberAndArticle(Member member, Article article);
    boolean existsByMemberAndArticleId(Member member, Long articleId);
}
