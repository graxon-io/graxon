package io.graxon.core.usecases.cluster;

import io.graxon.core.entities.cluster.ClusterStatus;
import io.graxon.core.entities.cluster.exceptions.ClusterNotFoundException;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 */
public interface UpdateClusterUseCase {

    ClusterDTO execute(

        @NotBlank
        String clusterId,

        @NotNull
        @Valid
        UpdateClusterRequest request

    ) throws ClusterNotFoundException;


    /**
     * @param status
     * @param url
     */
    record UpdateClusterRequest(

        @NotNull
        ClusterStatus status,

        @NotBlank
        String url

    ) {
    }
}

