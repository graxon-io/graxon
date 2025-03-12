package io.graxon.admin.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@ConfigurationProperties(prefix = "actuator.user")
public record ActuatorProperties(
        String name,
        String password
) {
}
