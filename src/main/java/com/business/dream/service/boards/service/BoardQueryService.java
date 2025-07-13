package com.business.dream.service.boards.service;

import com.business.dream.domain.repository.BoardJapRepository;
import com.business.dream.service.boards.model.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b> BoardQueryService </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */

@RequiredArgsConstructor

@Service
public class BoardQueryService {
    private final BoardJapRepository repository;


    public List<BoardVo> findAll() {
        return repository.findAll()
                .stream()
                .map(entity ->
                        BoardVo.builder()
                                .id(entity.getId())
                                .title(entity.getTitle())
                                .content(entity.getContent())
                                .build()
                ).toList();
    }
}
