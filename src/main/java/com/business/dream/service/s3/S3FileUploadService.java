package com.business.dream.service.s3;

import com.business.dream.service.model.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <b> S3FileUploadService </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-06-01
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    // 허용되는 파일 확장자
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
        "jpg", "jpeg", "png", "gif", "bmp", "webp",  // 이미지
        "pdf", "doc", "docx", "txt", "hwp",          // 문서
        "mp4", "avi", "mov", "wmv"                   // 동영상
    );

    // 최대 파일 크기 (10MB)
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * 파일을 S3에 업로드
     */
    public FileUploadResponse uploadFile(MultipartFile file, String directory) {
        try {
            // 파일 유효성 검증
            validateFile(file);

            // 저장할 파일명 생성
            String storedFileName = generateStoredFileName(file.getOriginalFilename());
            
            // S3 키 생성 (디렉토리/파일명)
            String s3Key = createS3Key(directory, storedFileName);

            // S3에 파일 업로드
            uploadToS3(file, s3Key);

            // 파일 URL 생성
            String fileUrl = generateFileUrl(s3Key);

            log.info("파일 업로드 성공: {}", fileUrl);

            return FileUploadResponse.builder()
                    .fileName(file.getOriginalFilename())
                    .storedFileName(storedFileName)
                    .fileUrl(fileUrl)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploadTime(LocalDateTime.now())
                    .directory(directory)
                    .build();

        } catch (Exception e) {
            log.error("파일 업로드 실패: {}", e.getMessage(), e);
            throw new RuntimeException("파일 업로드에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 여러 파일을 동시에 업로드
     */
    public List<FileUploadResponse> uploadMultipleFiles(List<MultipartFile> files, String directory) {
        return files.stream()
                .map(file -> uploadFile(file, directory))
                .toList();
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String s3Key) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            s3Client.deleteObject(deleteRequest);
            log.info("파일 삭제 성공: {}", s3Key);

        } catch (Exception e) {
            log.error("파일 삭제 실패: {}", e.getMessage(), e);
            throw new RuntimeException("파일 삭제에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 파일 존재 여부 확인
     */
    public boolean isFileExists(String s3Key) {
        try {
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            s3Client.headObject(headRequest);
            return true;

        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            log.error("파일 존재 확인 실패: {}", e.getMessage(), e);
            throw new RuntimeException("파일 존재 확인에 실패했습니다: " + e.getMessage(), e);
        }
    }

    // === Private 메서드들 ===

    /**
     * 파일 유효성 검증
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일 크기가 10MB를 초과할 수 없습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. 허용 형식: " + ALLOWED_EXTENSIONS);
        }
    }

    /**
     * 저장될 파일명 생성 (중복 방지)
     */
    private String generateStoredFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    /**
     * S3 키 생성
     */
    private String createS3Key(String directory, String fileName) {
        if (StringUtils.isBlank(directory)) {
            return fileName;
        }
        return directory + "/" + fileName;
    }

    /**
     * 실제 S3 업로드 수행
     */
    private void uploadToS3(MultipartFile file, String s3Key) throws IOException {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
        
        s3Client.putObject(putRequest, requestBody);
    }

    /**
     * 파일 URL 생성
     */
    private String generateFileUrl(String s3Key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", 
                            bucketName, region, s3Key);
    }

    /**
     * 파일 확장자 추출
     */
    private String getFileExtension(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "";
        }
        
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        
        return filename.substring(lastDotIndex + 1);
    }
}