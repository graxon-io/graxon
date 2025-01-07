package io.graxon.core.node.datasource;

import io.graxon.library.node.NodeRegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

/**
 *
 */
@Repository
public class NodeConnector {

    //
    private static final Logger log = LoggerFactory.getLogger(NodeConnector.class);

    //
    private final WebClient webClient;

    /**
     */
    public NodeConnector(@Qualifier("mtlsClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     */
    public Optional<NodeRegistrationRequest> connect(String hostname) {
        return webClient.get()
            .uri(builder -> builder
                .scheme("https")
                .host(hostname)
                .port(8802)
                .path("/connect")
                .build())
            .header("Content-Type", "application/json")
            .retrieve()
            .bodyToMono(NodeRegistrationRequest.class)
            .doOnError(e -> log.error("Failed to connect!", e))
            .doOnSuccess(s -> log.info("Successfully connected: {}", s))
            .blockOptional();
    }
}
