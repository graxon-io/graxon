package io.graxon.core.cluster.controllers;

import io.graxon.core.usecases.cluster.GetClusterUseCase;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Tag(name = "Cluster")
@RestController
@RequestMapping("/api/v1/clusters")
public class GetClusterController {

    //
    private final GetClusterUseCase useCase;

    /**
     *
     * @param useCase
     */
    public GetClusterController(GetClusterUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     * @return
     */
    @GetMapping("/{cluster_id}")
    public ClusterDTO fetchClusters(
        @PathVariable("cluster_id") String clusterId) {
        return useCase.execute(clusterId);
    }
}
