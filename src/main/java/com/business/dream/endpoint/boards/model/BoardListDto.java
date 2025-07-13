package com.business.dream.endpoint.boards.model;

import com.business.dream.service.boards.model.BoardVo;
import lombok.*;

import java.util.List;

/**
 * <b> BoardListDto </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardListDto {

    @Getter
    @Setter
    public static class Request {

    }

    @Getter
    @Setter
    @Builder
    public static class Response {

        private List<Board> items;

        public static class ResponseBuilder {
            public ResponseBuilder from(List<BoardVo> boards) {
                if (boards == null) {
                    this.items = List.of(); // 빈 리스트로 초기화
                    return this;
                }

                List<Board> list = boards.stream()
                        .map(vo -> Board.builder()
                                .id(vo.getId())
                                .title(vo.getTitle())
                                .content(vo.getContent())
                                .build())
                        .toList();

                this.items = list;
                return this;
            }
        }
    }

    @Getter
    @Builder
    public static class Board{
        private Long id;
        private String title;
        private String content;
    }

}
