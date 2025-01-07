package io.graxon.wait_for_keystore;

import io.graxon.library.node.NodeRegistrationRequest;
import io.graxon.library.node.NodeRegistrationResponse;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 *
 */
public class Connector {
    // --------------------------------------------------------------------------------------------

    //
    private static final Logger log = LoggerFactory.getLogger(Connector.class);

    //
    private final WebClient webClient;

    /**
     *
     */
    public Connector(ConnectionProperties properties, String applicationName) throws SSLException {

        // create ssl context to trust all certificates
        SslContext sslContext = SslContextBuilder.forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build();

        // create http client
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        // create web client
        webClient = WebClient.builder()
            .baseUrl(properties.url())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    /**
     */
    public Optional<NodeRegistrationResponse> register() {
        return webClient.post()
            .uri("/api/v1/node/register")
            .bodyValue(
                new NodeRegistrationRequest(
                    getCluster(),
                    "todo",
                    "nodeId",
                    getNamespace(),
                    getPod()
                ))
            .retrieve()
            .bodyToMono(NodeRegistrationResponse.class)
            .doOnSuccess(output -> log.info("node registered: {}", output))
            .doOnError(error -> log.warn("node registration failed:", error))
            .blockOptional();
    }

    /**
     *
     * @return
     */
    public void fetchKeystore(String id) {
        Flux<DataBuffer> flux = webClient.get()
            .uri("/api/v1/node/register/" + id)
            .retrieve()
            .bodyToFlux(DataBuffer.class);

        Path path = Paths.get("keystore.p12");
        DataBufferUtils.write(flux, path).block();
    }

    /**
     *
     */
    private String getNamespace() {
        return Optional.ofNullable(System.getenv("GRAXON_NODE_NAMESPACE")).orElse("unknown");
    }

    /**
     *
     */
    private String getPod() {
        return Optional.ofNullable(System.getenv("GRAXON_NODE_POD")).orElse("unknown");
    }

    /**
     */
    private String getCluster() {
        return Optional.ofNullable(System.getenv("GRAXON_NODE_CLUSTER")).orElse("unknown");
    }



}
