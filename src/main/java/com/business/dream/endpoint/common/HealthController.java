package com.business.dream.endpoint.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b> HealthController </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-04-03
 */

@RestController
public class HealthController {

    @GetMapping("/health")
    public String healthCheck() {
        return "UP";
    }



}
