package com.business.dream.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b> FileUploadRequest </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-06-01
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadRequest {
    private String directory;  // 저장할 디렉토리 (예: "images", "documents")
    private String description; // 파일 설명
}