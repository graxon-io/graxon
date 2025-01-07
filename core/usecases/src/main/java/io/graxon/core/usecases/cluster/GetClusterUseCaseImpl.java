package io.graxon.core.usecases.cluster;

import io.graxon.core.entities.cluster.exceptions.ClusterNotFoundException;
import io.graxon.core.entities.cluster.gateway.ClusterGateway;
import io.graxon.core.entities.node.gateway.NodeGateway;
import io.graxon.core.usecases.cluster.dto.ClusterDTO;
import io.graxon.core.usecases.node.dto.NodeDTO;
import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@UseCase
class GetClusterUseCaseImpl implements GetClusterUseCase {
    // --------------------------------------------------------------------------------------------

    //
    private final Logger log = LoggerFactory.getLogger(GetClusterUseCaseImpl.class);

    //
    private final ClusterGateway clusterGateway;
    private final NodeGateway nodeGateway;

    /**
     * Constructor
     *
     * @param gateway ClusterGateway
     */
    GetClusterUseCaseImpl(ClusterGateway gateway, NodeGateway nodeGateway) {
        this.clusterGateway = gateway;
        this.nodeGateway = nodeGateway;
    }

    /**
     */
    @Override
    public ClusterDTO execute(String clusterId) throws ClusterNotFoundException {
        log.info("getting cluster: {}", clusterId);
        var cluster = clusterGateway.findById(clusterId).orElseThrow(ClusterNotFoundException::new);
        var nodes = nodeGateway.findByCluster(cluster.id()).stream().map(NodeDTO::new).toList();
        return new ClusterDTO(cluster, nodes);
    }

}
