package com.traveloper.tourfinder.board.controller;

import com.traveloper.tourfinder.board.dto.BoardDto;
import com.traveloper.tourfinder.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 전체 조회
    @GetMapping
    public String listAllBoards() {
        return null;
    }

    // 게시글 상세보기
    @GetMapping("/{articleId}")
    public String listOneBoard(
            @PathVariable("boardId")
            Long boardId
    ) {
        BoardDto boardDto = boardService.read(boardId);
        return null;
    }
}
