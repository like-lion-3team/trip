package com.traveloper.tourfinder.board.repo;

import com.traveloper.tourfinder.board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
