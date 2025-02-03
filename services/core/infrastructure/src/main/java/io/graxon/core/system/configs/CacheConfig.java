package io.graxon.core.system.configs;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 */
@Tag(name = "Cache")
@Profile(value = { "!nocache", "!test" })
@EnableCaching
@Configuration
public class CacheConfig {
    // --------------------------------------------------------------------------------------------

    //
    public static final String CACHE_PROJECTS = "projects";
    public static final String CACHE_PROJECT = "project";

}
