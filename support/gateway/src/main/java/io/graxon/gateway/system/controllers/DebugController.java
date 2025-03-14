package io.graxon.gateway.system.controllers;

import io.graxon.gateway.system.exceptions.CustomRequestAuthException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 *
 */
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/gateway")
public class DebugController {


    private static final Logger log = LoggerFactory.getLogger(DebugController.class);

    /**
     *
     */
    @GetMapping("/debug")
    public String debug() {
        log.info("debugging...");
        return "gateway-" + UUID.randomUUID().toString();
    }

    /**
     *
     */
    @GetMapping("/error")
    public String error() {
        throw new CustomRequestAuthException("error message...");
    }
}
