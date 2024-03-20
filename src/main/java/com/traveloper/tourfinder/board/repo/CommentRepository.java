package com.traveloper.tourfinder.board.repo;

import com.traveloper.tourfinder.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
