package io.graxon.gateway.system.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 */
@Component
public class GlobalRedirectionFilter implements GlobalFilter {

    /**
     *
     */
    private final Logger log = LoggerFactory.getLogger(GlobalRedirectionFilter.class);

    /**
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("-----> GLOBAL FILTER");
        exchange.getRequest().getHeaders().forEach((key, value) -> {
            System.out.println("HEADER: " + key + " = " + value);
        });
        return chain.filter(exchange);
    }
}
