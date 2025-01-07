package io.graxon.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 */
@RestController
public class DebugController {
    // --------------------------------------------------------------------------------------------

    //
    private static final Logger log = LoggerFactory.getLogger(DebugController.class);

    //
    private final WebClient webClient;

    /**
     */
    public DebugController(@Qualifier("mtlsClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     */
    @GetMapping("/debug")
    public String get() {
        return webClient.get()
            .uri("https://graxon-node:8801/debug")
            .header("Content-Type", "application/json")
            .retrieve()
            .bodyToMono(String.class)
            .doOnError(e -> log.error("Failed to connect!", e))
            .doOnSuccess(s -> log.info("Successfully connected: {}", s))
            .blockOptional()
            .map(s -> "Successfully connected: " + s)
            .orElse("Failed to connect!");
    }
}
