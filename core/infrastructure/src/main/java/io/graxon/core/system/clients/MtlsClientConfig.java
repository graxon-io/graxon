package io.graxon.core.system.clients;

import io.graxon.core.system.MtlsClientProperties;
import io.netty.handler.ssl.SslContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(MtlsClientProperties.class)
public class MtlsClientConfig {
    // --------------------------------------------------------------------------------------------

    //
    private static final Logger log = LoggerFactory.getLogger(MtlsClientConfig.class);

    //
    private final MtlsClientProperties properties;

    /**
     */
    public MtlsClientConfig(MtlsClientProperties properties) {
        this.properties = properties;
    }

    /**
     */
    @Bean(value = "mtlsClient")
    WebClient initWebClient() throws Exception {
        log.info("Initializing WebClient with MTLS");

        // load the keystore (ca certificate)
        var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(properties.keyStore().getInputStream(), properties.keyStorePassword().toCharArray());

        // load the truststore (client certificates)
        var trustStore = getTrustStore();

        // get the certificates as an array
        X509Certificate[] certificates =
            Collections.list(trustStore.aliases()).stream()
                .map(alias -> getCertificate(trustStore, alias))
                .map(cert -> (X509Certificate) cert)
                .filter(this::validateCertificate)
                .toArray(X509Certificate[]::new);

        // log the certificates
        Arrays.stream(certificates).forEach(certificate ->
            log.trace("Certificate: {}", certificate.getSubjectX500Principal().toString()));

        // create the key manager factory with the keystore
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, properties.keyStorePassword().toCharArray());

        // create the ssl context
        var sslContext =
            SslContextBuilder.forClient()
                .keyManager(keyManagerFactory)
                .trustManager(certificates)
                .build();

        // create the http client
        var httpClient = HttpClient.create()
            .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        // create the client connector
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        // create the web client
        return WebClient.builder()
            //.clientConnector(connector)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filters(exchangeFilterFunctions -> {
                exchangeFilterFunctions.add(logRequest());
                exchangeFilterFunctions.add(logResponse());
            })
            .build();
    }

    // --------------------------------------------------------------------------------------------

    /**
     *
     */
    private KeyStore getTrustStore() throws Exception {
        var trustStoreFile = new File(properties.trustStore());
        var trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        if (trustStoreFile.exists()) {
            log.trace("loading truststore from file: {}", trustStoreFile);
            try (InputStream is = new FileInputStream(trustStoreFile)) {
                trustStore.load(is, properties.trustStorePassword().toCharArray());
            }
        } else {
            log.trace("creating truststore file: {}", trustStoreFile);
            try(OutputStream os = new FileOutputStream(trustStoreFile)) {
                trustStore.load(null, null);
                trustStore.store(os, properties.trustStorePassword().toCharArray());
            }
        }
        return trustStore;
    }

    /**
     */
    private ExchangeFilterFunction logRequest() {
        return (clientRequest, exchangeFunction) -> {
            log.info("Client Request: {} {} {}", clientRequest.method(), clientRequest.url(), clientRequest.body());
            clientRequest.headers()
                .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return exchangeFunction.exchange(clientRequest);
        };
    }

    /**
     */
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Client Response Status: {}", clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders()
                .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientResponse);
        });
    }

    /**
     */
    private Certificate getCertificate(KeyStore store, String alias) {
        try {
            return store.getCertificate(alias);
        } catch (KeyStoreException exception) {
            throw new RuntimeException("Error reading truststore", exception);
        }
    }

    /**
     */
    private boolean validateCertificate(X509Certificate certificate) {
        var certificateExpirationDate =
            certificate.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        var certificateStartDate =
            certificate.getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (LocalDate.now().isAfter(certificateExpirationDate)) {
            throw new RuntimeException("Service date expiration");
        }

        if (LocalDate.now().isBefore(certificateStartDate)) {
            throw new RuntimeException(
                "Service cannot be used until " + certificateStartDate.toString());
        }
        return true;
    }
}
