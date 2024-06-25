package com.kidzcubicle.uaps.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public String healthCheck() {
        log.debug("REST request to check health");
        return "I am working";
    }
}
