package io.graxon.core.system.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.graxon.core.system.converters.AccountConverter;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 *
 */
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/v1/core")
public class DebugController {

    //
    private static final Logger log = LoggerFactory.getLogger(DebugController.class);

    /**
     *
     */
    @GetMapping("/debug")
    public String debug() {
        log.info("debugging...");
        return "notifier-" + UUID.randomUUID().toString();
    }

    /**
     *
     */
    @GetMapping("/headers")
    public String headers(
        @Parameter(hidden = true) @RequestHeader(value = "X-User-Account") String account,
        @Parameter(hidden = true) @RequestHeader("X-User-Mail") String email,
        @Parameter(hidden = true) @RequestHeader("X-User-Name") String name) {
        return String.format("account: %s, email: %s, name: %s", account, email, name);
    }

    /**
     *
     * @param account
     * @return
     */
    @GetMapping("/yolo")
    public String yolo(
        @Parameter(hidden = true) @RequestHeader("X-Account") AccountConverter.Account account) {
        return String.format("account: %s, email: %s, name: %s", account.id(), account.email(), account.name());
    }
}
