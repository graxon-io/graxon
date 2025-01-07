package io.graxon.core.usecases.cluster;

import io.graxon.core.entities.cluster.gateway.ClusterGateway;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
@UseCase
class FetchClustersUseCaseImpl implements FetchClustersUseCase {
    // --------------------------------------------------------------------------------------------

    //
    private final Logger log = LoggerFactory.getLogger(FetchClustersUseCaseImpl.class);

    //
    private final ClusterGateway gateway;

    /**
     * Constructor
     *
     * @param gateway ClusterGateway
     */
    FetchClustersUseCaseImpl(ClusterGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Fetch all clusters
     *
     * @return list of clusters
     */
    @Override
    public List<ClusterDTO> execute() {
        log.info("fetching all clusters");
        return gateway.findAll().stream().map(ClusterDTO::new).toList();
    }
}
