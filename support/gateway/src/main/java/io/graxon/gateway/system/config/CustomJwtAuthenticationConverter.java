package io.graxon.gateway.system.config;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class CustomJwtAuthenticationConverter implements Converter<Jwt, Mono<? extends AbstractAuthenticationToken>> {

    //
    private static final Logger log = LoggerFactory.getLogger(CustomJwtAuthenticationConverter.class);

    //
    private static final String DEFAULT_AUTHORITY_PREFIX = "ROLE_";

    //
    private static final String ROLES_CLAIM = "roles";
    private static final String RESOURCE_ACCESS_CLAIM = "resource_access";
    private static final String CLIENT_CLAIM = "graxon";

    /**
     *
     * @param jwt
     * @return
     */
    @Override
    public Mono<? extends AbstractAuthenticationToken> convert(Jwt jwt) {
        return Mono.just(new JwtAuthenticationToken(jwt, Stream
            .concat(new JwtGrantedAuthoritiesConverter().convert(jwt).stream(), extractClientRoles(jwt).stream())
            .collect(Collectors.toSet())));
    }

    /**
     * @param jwt
     * @return
     */
    private Collection<? extends GrantedAuthority> extractClientRoles(Jwt jwt) {
        log.info("-------------------> OAUTH EXTRACTION <-------------------");
        var roles = new ArrayList<>();
        if (jwt != null) {
            try {
                LinkedTreeMap<String, LinkedTreeMap<String, List<String>>> resourceAccess = jwt.getClaim(RESOURCE_ACCESS_CLAIM);
                LinkedTreeMap<String, List<String>> clientAccess = resourceAccess.get(CLIENT_CLAIM);
                roles.addAll(clientAccess.get(ROLES_CLAIM));
            } catch (Exception e) {
                log.warn("failed to extract client_roles: {}", e.getMessage());
            }
        }
        return roles.isEmpty() ? Collections.emptySet() : roles.stream().map(role -> new SimpleGrantedAuthority(DEFAULT_AUTHORITY_PREFIX + role)).collect(Collectors.toSet());
    }
}
