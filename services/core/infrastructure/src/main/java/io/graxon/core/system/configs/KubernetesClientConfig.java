package io.graxon.core.system.configs;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 */
@Profile("kubernetes")
@Configuration
public class KubernetesClientConfig {
    // --------------------------------------------------------------------------------------------

    //
    public static final String LABEL_MANAGED_BY = "app.kubernetes.io/managed-by";
    public static final String LABEL_MANAGED_BY_VALUE = "GRAXON";

    //
    public static final String LABEL_GRAXON_PROJECT = "graxon.io/project";
    public static final String LABEL_GRAXON_CLUSTER = "graxon.io/cluster";

    /**
     *
     */
    @Bean
    KubernetesClient kubernetesClient() {
        return new KubernetesClientBuilder().build();
    }

}
