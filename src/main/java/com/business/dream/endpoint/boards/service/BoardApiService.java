package com.business.dream.endpoint.boards.service;

import com.business.dream.endpoint.boards.model.BoardListDto;
import com.business.dream.service.boards.model.BoardVo;
import com.business.dream.service.boards.service.BoardQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b> BoardApiService </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */

@RequiredArgsConstructor

@Service
public class BoardApiService {
    private final BoardQueryService service;


    public BoardListDto.Response getBoardList() {
        List<BoardVo> list = this.service.findAll();

        return BoardListDto.Response.builder()
                .from(list)
                .build();
    }

}
