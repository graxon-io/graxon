package io.graxon.core.usecases.cluster;

import io.graxon.core.usecases.cluster.dto.ClusterDTO;

import java.util.List;

/**
 *
 */
public interface FetchClustersUseCase {

    /**
     * Fetch all clusters
     *
     * @return list of clusters
     */
    List<ClusterDTO> execute();

}
