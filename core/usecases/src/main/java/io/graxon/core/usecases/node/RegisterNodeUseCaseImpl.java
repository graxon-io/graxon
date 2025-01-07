package io.graxon.core.usecases.node;

import io.graxon.core.entities.cluster.Cluster;
import io.graxon.core.entities.cluster.ClusterStatus;
import io.graxon.core.entities.cluster.exceptions.ClusterNotCreatedException;
import io.graxon.core.entities.cluster.gateway.ClusterGateway;
import io.graxon.core.entities.node.Node;
import io.graxon.core.entities.node.exceptions.NodeAlreadyExistsException;
import io.graxon.core.entities.node.exceptions.NodeNotConnectedException;
import io.graxon.core.entities.node.exceptions.NodeNotCreatedException;
import io.graxon.core.entities.node.gateway.NodeGateway;
import io.graxon.library.node.NodeRegistrationResponse;
import io.graxon.library.node.NodeStatus;
import io.graxon.library.node.NodeRegistrationRequest;
import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 *
 */
@UseCase
class RegisterNodeUseCaseImpl implements RegisterNodeUseCase {
    // --------------------------------------------------------------------------------------------

    //
    private final Logger log = LoggerFactory.getLogger(RegisterNodeUseCaseImpl.class);

    //
    private final ClusterGateway clusterGateway;
    private final NodeGateway nodeGateway;

    /**
     * Constructor
     *
     * @param clusterGateway cluster gateway
     * @param nodeGateway    node gateway
     */
    RegisterNodeUseCaseImpl(ClusterGateway clusterGateway, NodeGateway nodeGateway) {
        this.clusterGateway = clusterGateway;
        this.nodeGateway = nodeGateway;
    }

    /**
     * Register node
     *
     * @param connection node registration request
     * @return node registration response
     */
    @Override
    public NodeRegistrationResponse execute(NodeRegistrationRequest connection) throws NodeNotConnectedException, NodeNotCreatedException {
        log.info("node registering: {}", connection);

        // init local date time
        LocalDateTime now = LocalDateTime.now();

        // find or create cluster
        var cluster = clusterGateway.findById(connection.clusterId()).orElse(
            clusterGateway.save(
                new Cluster(
                    connection.clusterId(),
                    connection.clusterName(),
                    null,
                    ClusterStatus.PENDING,
                    now,
                    now
                )).orElseThrow(ClusterNotCreatedException::new));

        // check if node already exists
        nodeGateway.findById(connection.nodeId()).ifPresent(e -> {
            throw new NodeAlreadyExistsException();
        });

        // create node
        var node = new Node(
            connection.nodeId(),
            NodeStatus.PENDING,
            now,
            now,
            cluster.id()
        );

        // save node
        return nodeGateway.save(node).map(e -> new NodeRegistrationResponse(e.status())).orElseThrow(NodeNotCreatedException::new);
    }
}
