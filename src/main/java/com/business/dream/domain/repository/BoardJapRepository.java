package com.business.dream.domain.repository;

import com.business.dream.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <b> BoardJapRepository </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */
public interface BoardJapRepository extends JpaRepository<BoardEntity, Long> {
}
