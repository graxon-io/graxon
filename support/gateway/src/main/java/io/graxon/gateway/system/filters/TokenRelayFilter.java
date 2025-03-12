package io.graxon.gateway.system.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
class TokenRelayFilter extends AbstractGatewayFilterFactory<Object> {

    private static final Logger log = LoggerFactory.getLogger(TokenRelayFilter.class);

    /**
     * Extracts the token from the request and adds it to the headers
     *
     * @param config configuration
     * @return GatewayFilter
     */
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> exchange.getPrincipal()
            .filter(principal -> principal instanceof JwtAuthenticationToken)
            .cast(JwtAuthenticationToken.class)
            .map(JwtAuthenticationToken::getTokenAttributes)
            .map(attributes -> {
                var mutatedRequest = exchange.getRequest().mutate()
                    .headers(httpHeaders -> {
                        httpHeaders.remove("authorization");

                        var username = attributes.getOrDefault("preferred_username", "unknown").toString();
                        var mail = attributes.getOrDefault("email", "unknown").toString();
                        var name = attributes.getOrDefault("name", "unknown").toString();

                        httpHeaders.add("X-User-Account", username);
                        httpHeaders.add("X-User-Mail", mail);
                        httpHeaders.add("X-User-Name", name);


                        //
                        try {
                            var account = new Account(username, name, mail);
                            var json = new ObjectMapper().writeValueAsString(account);
                            log.info("Account: {}", json);
                            httpHeaders.add("X-Account", json);
                        } catch (Exception e) {
                            log.warn("Failed to convert Account to JSON: {}", e.getMessage());
                        }
                    })
                    .build();
                return exchange.mutate().request(mutatedRequest).build();
            })
            .defaultIfEmpty(exchange)
            .flatMap(chain::filter);
    }



    public record Account
        (String id, String name, String email) {
    }
}
