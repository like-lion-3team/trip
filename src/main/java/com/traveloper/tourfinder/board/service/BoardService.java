package com.traveloper.tourfinder.board.service;

import com.traveloper.tourfinder.board.dto.BoardDto;
import com.traveloper.tourfinder.board.repo.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public static Object findBoardByarticleId(Long id) {
        return null;
    }

    public List<BoardDto> readAll() {
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board: boardRepository.findAll()) {
            boardDtos.add(BoardDto.fromEntity(board));
        }
        return boardDtos;
    }

    public BoardDto read(Long boardId) {
        return BoardDto.fromEntity(
                boardRepository.findById(boardId).orElseThrow());
    }
}
