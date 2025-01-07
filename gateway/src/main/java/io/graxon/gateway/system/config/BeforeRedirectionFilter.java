package io.graxon.gateway.system.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
class BeforeRedirectionFilter extends AbstractGatewayFilterFactory<Object> {

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(BeforeRedirectionFilter.class);

    /**
     *
     */
    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            logger.info("-------------------> BEFORE REDIRECTION <-------------------");
            return chain.filter(exchange);
        }, 0);
    }

}
