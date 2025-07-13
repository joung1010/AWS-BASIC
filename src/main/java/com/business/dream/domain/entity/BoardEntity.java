package com.business.dream.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b> BoardEntity </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "boards")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

}