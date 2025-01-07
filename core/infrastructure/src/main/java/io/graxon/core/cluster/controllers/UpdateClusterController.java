package io.graxon.core.cluster.controllers;

import io.graxon.core.usecases.cluster.UpdateClusterUseCase;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Tag(name = "Cluster")
@RestController
@RequestMapping("/api/v1/clusters")
public class UpdateClusterController {

    //
    private final UpdateClusterUseCase useCase;

    /**
     *
     * @param useCase
     */
    public UpdateClusterController(UpdateClusterUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     * @return
     */
    @PutMapping("/{cluster_id}")
    public ClusterDTO updateCluster(
        @PathVariable("cluster_id") String clusterId,
        @RequestBody UpdateClusterUseCase.UpdateClusterRequest request) {
        return useCase.execute(clusterId, request);
    }
}
