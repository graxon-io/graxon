package io.graxon.core.usecases.cluster;

import io.graxon.core.entities.cluster.Cluster;
import io.graxon.core.entities.cluster.exceptions.ClusterNotFoundException;
import io.graxon.core.entities.cluster.gateway.ClusterGateway;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 *
 */
@UseCase
class UpdateClusterUseCaseImpl implements UpdateClusterUseCase {
    // --------------------------------------------------------------------------------------------

    //
    private final Logger log = LoggerFactory.getLogger(UpdateClusterUseCaseImpl.class);

    //
    private final ClusterGateway gateway;

    /**
     * Constructor
     *
     * @param gateway ClusterGateway
     */
    UpdateClusterUseCaseImpl(ClusterGateway gateway) {
        this.gateway = gateway;
    }

    /**
     *
     * @param clusterId
     * @param request
     * @return
     * @throws ClusterNotFoundException
     */
    @Override
    public ClusterDTO execute(String clusterId, UpdateClusterRequest request) throws ClusterNotFoundException {
        log.info("update cluster: {}", clusterId);
        var cluster = gateway.findById(clusterId).orElseThrow(ClusterNotFoundException::new);

        //
        var entity = new Cluster(
            cluster.id(),
            cluster.name(),
            request.url(),
            request.status(),
            cluster.createdAt(),
            LocalDateTime.now()
        );

        //
        return gateway.update(entity).map(ClusterDTO::new).orElseThrow(ClusterNotFoundException::new);
    }
}
