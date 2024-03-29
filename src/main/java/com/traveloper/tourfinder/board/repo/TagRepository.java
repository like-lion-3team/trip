package com.traveloper.tourfinder.board.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.traveloper.tourfinder.board.entity.Tag;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


public interface TagRepository extends JpaRepository<Tag, Long> {
}
