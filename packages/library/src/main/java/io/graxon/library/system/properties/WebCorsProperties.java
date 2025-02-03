package io.graxon.library.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@SuppressWarnings("unused")
@ConfigurationProperties(prefix = "web")
public record WebCorsProperties(
    Cors cors
) {
    /**
     *
     * @param allowedOrigins
     * @param allowedMethods
     * @param maxAge
     * @param allowedHeaders
     * @param exposedHeaders
     */
    public record Cors(
        String[] allowedOrigins,
        String[] allowedMethods,
        long maxAge,
        String[] allowedHeaders,
        String[] exposedHeaders) {
    }
}
