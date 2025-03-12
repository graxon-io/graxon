package io.graxon.gateway.system.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Web Security Configuration
 */
@Configuration
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
@EnableWebFluxSecurity
public class WebSecurityConfig {
    // --------------------------------------------------------------------------------------------

    //
    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    //
    private final String[] WHITELIST = {
        "/",
        "/webjars/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/*/v3/api-docs"
    };

    /**
     * OAuth Security Filter
     *
     * @param http ServerHttpSecurity
     * @return SecurityWebFilterChain
     */
    @Bean
    SecurityWebFilterChain oauthSecurityFilter(ServerHttpSecurity http) {
        return http.authorizeExchange(auth -> auth
                .pathMatchers(WHITELIST).permitAll()
                .pathMatchers("/actuator/**").authenticated()
                //.pathMatchers("/api/admin/**").hasAnyRole("ADMIN", "MANAGER")
                .anyExchange().authenticated())
            .httpBasic(withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(new CustomJwtAuthenticationConverter())
            ))
            .build();
    }

    // --------------------------------------------------------------------------------------------
    // actuator security settings

    /**
     * User Details Service
     *
     * @param username username
     * @param password password
     * @return MapReactiveUserDetailsService
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService(
        PasswordEncoder passwordEncoder,
        @Value("${spring.security.user.name}") String username,
        @Value("${spring.security.user.password}") String password) {
        return new MapReactiveUserDetailsService(User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .build());
    }

    /**
     * Password Encoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}