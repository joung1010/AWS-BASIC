package com.business.dream.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <b> FileUploadResponse </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-06-01
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    private String fileName;          // 원본 파일명
    private String storedFileName;    // S3에 저장된 파일명
    private String fileUrl;           // S3 파일 접근 URL
    private String contentType;       // 파일 타입
    private Long fileSize;            // 파일 크기 (bytes)
    private LocalDateTime uploadTime; // 업로드 시간
    private String directory;         // 저장 디렉토리
}