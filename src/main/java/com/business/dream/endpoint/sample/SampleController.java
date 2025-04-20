package com.business.dream.endpoint.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b> SampleController </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-04-20
 */


@RequestMapping("/api/sample")
@RestController
public class SampleController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
