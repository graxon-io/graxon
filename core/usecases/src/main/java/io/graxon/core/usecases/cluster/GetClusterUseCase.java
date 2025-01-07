package io.graxon.core.usecases.cluster;

import io.graxon.core.entities.cluster.exceptions.ClusterNotFoundException;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import jakarta.validation.constraints.NotBlank;

/**
 *
 */
public interface GetClusterUseCase {

    ClusterDTO execute(@NotBlank String clusterId) throws ClusterNotFoundException;

}
