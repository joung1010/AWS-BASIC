package com.business.dream.service.boards.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

/**
 * <b> BoardVo </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */

@Getter
@Builder
public class BoardVo {

    private Long id;

    private String title;

    private String content;
}
