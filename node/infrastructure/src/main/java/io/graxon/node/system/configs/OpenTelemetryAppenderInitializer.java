package io.graxon.node.system.configs;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * See <a href="https://docs.spring.io/spring-boot/3.4-SNAPSHOT/reference/actuator/loggers.html#actuator.loggers.opentelemetry">link</a>
 */
@Component
@Profile("opentelemetry")
class OpenTelemetryAppenderInitializer implements InitializingBean {
    // ------------------------------------------------------------

    //
    private final OpenTelemetry openTelemetry;

    /**
     * constructor
     * @param openTelemetry openTelemetry instance
     */
    OpenTelemetryAppenderInitializer(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    /**
     * install the OpenTelemetryAppender
     */
    @Override
    public void afterPropertiesSet() {
        OpenTelemetryAppender.install(this.openTelemetry);
    }
    
}


