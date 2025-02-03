package io.graxon.wait_for_keystore;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@ConfigurationProperties(prefix = "graxon.core")
public record ConnectionProperties(
   String url
) {
}
