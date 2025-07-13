package com.business.dream.endpoint.boards.controller;

import com.business.dream.endpoint.boards.model.BoardListDto;
import com.business.dream.endpoint.boards.service.BoardApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b> BoardsApiController </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardsApiController {

    public final BoardApiService service;

    @GetMapping("/boards")
    public BoardListDto.Response getBoards() {
        return service.getBoardList();
    }
}
