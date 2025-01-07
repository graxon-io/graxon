package io.graxon.core.cluster.controllers;

import io.graxon.core.usecases.cluster.FetchClustersUseCase;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@Tag(name = "Cluster")
@RestController
@RequestMapping("/api/v1/clusters")
public class FetchClustersController {

    //
    private final FetchClustersUseCase useCase;

    /**
     *
     * @param useCase
     */
    public FetchClustersController(FetchClustersUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     * @return
     */
    @GetMapping
    public List<ClusterDTO> fetchClusters() {
        return useCase.execute();
    }
}
