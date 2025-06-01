package com.business.dream.endpoint.files;

import com.business.dream.service.model.FileUploadResponse;
import com.business.dream.service.s3.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <b> FileUploadController </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-06-01
 */

@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3FileUploadService s3FileUploadService;

    /**
     * 단일 파일 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "directory", defaultValue = "uploads") String directory) {
        
        log.info("파일 업로드 요청: {} (크기: {} bytes, 디렉토리: {})", 
                file.getOriginalFilename(), file.getSize(), directory);

        FileUploadResponse response = s3FileUploadService.uploadFile(file, directory);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 여러 파일 업로드
     */
    @PostMapping("/upload-multiple")
    public ResponseEntity<List<FileUploadResponse>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "directory", defaultValue = "uploads") String directory) {
        
        log.info("다중 파일 업로드 요청: {} 개 파일, 디렉토리: {}", files.size(), directory);

        List<FileUploadResponse> responses = s3FileUploadService.uploadMultipleFiles(files, directory);
        
        return ResponseEntity.ok(responses);
    }

    /**
     * 파일 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("s3Key") String s3Key) {
        log.info("파일 삭제 요청: {}", s3Key);

        s3FileUploadService.deleteFile(s3Key);
        
        return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
    }

    /**
     * 파일 존재 여부 확인
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkFileExists(@RequestParam("s3Key") String s3Key) {
        boolean exists = s3FileUploadService.isFileExists(s3Key);
        return ResponseEntity.ok(exists);
    }
}