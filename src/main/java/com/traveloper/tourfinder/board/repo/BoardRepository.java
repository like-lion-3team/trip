package com.traveloper.tourfinder.board.repo;

import com.traveloper.tourfinder.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}