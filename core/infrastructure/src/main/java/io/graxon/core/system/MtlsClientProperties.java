package io.graxon.core.system;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 *
 */
@ConfigurationProperties(prefix = "client.ssl")
public record MtlsClientProperties(
    Resource keyStore,
    String keyStorePassword,
    String trustStore,
    String trustStorePassword


) {

}
