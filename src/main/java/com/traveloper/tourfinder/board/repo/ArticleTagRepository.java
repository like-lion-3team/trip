package com.traveloper.tourfinder.board.repo;

import java.util.List;
import com.traveloper.tourfinder.board.entity.ArticleTag;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findArticleTagByArticleId(Long id);
}
