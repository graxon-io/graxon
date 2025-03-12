package io.graxon.admin.system.config;

import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import io.graxon.admin.system.properties.ActuatorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(ActuatorProperties.class)
public class WebClientSetup {

    //
    private final ActuatorProperties actuatorProperties;

    /**
     * Constructor
     *
     * @param actuatorProperties actuator properties
     */
    public WebClientSetup(ActuatorProperties actuatorProperties) {
        this.actuatorProperties = actuatorProperties;
    }

    /**
     * Custom http headers provider
     *
     * @return http headers provider
     */
    @Bean
    public HttpHeadersProvider customHttpHeadersProvider() {
        return instance -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(actuatorProperties.name(), actuatorProperties.password());
            return headers;
        };
    }
}
