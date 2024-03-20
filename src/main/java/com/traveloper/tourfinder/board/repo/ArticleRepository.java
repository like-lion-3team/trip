package com.traveloper.tourfinder.board.repo;

import com.traveloper.tourfinder.board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
