package io.graxon.core.usecases.node;

import io.graxon.core.entities.cluster.exceptions.ClusterPendingStateException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.core.io.Resource;

/**
 *
 */
public interface DownloadKeystoreUseCase {

    Resource execute(@NotBlank String clusterId, @NotBlank String nodeId) throws ClusterPendingStateException;

}
