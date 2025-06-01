package com.business.dream.endpoint.files;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b> FileTestController </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-06-01
 */

@Controller
@RequestMapping("/test")
public class FileTestController {

    /**
     * 파일 업로드 테스트 페이지
     */
    @GetMapping("/upload")
    public String fileUploadTestPage() {
        return "file-upload-test";
    }


}