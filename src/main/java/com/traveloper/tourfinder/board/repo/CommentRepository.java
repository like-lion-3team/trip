package com.traveloper.tourfinder.board.repo;

import com.traveloper.tourfinder.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.traveloper.tourfinder.board.dto.CommentDto;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
