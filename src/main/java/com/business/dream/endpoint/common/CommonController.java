package com.business.dream.endpoint.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b> CommonController </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-04-03
 */

@Controller
@RequestMapping("/api/common")
public class CommonController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "UP";
    }



}
