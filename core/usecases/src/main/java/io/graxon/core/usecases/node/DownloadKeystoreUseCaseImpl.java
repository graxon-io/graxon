package io.graxon.core.usecases.node;

import io.graxon.core.entities.cluster.ClusterStatus;
import io.graxon.core.entities.cluster.exceptions.*;
import io.graxon.core.entities.cluster.gateway.ClusterGateway;
import io.graxon.core.entities.node.Node;
import io.graxon.core.entities.node.exceptions.*;
import io.graxon.core.entities.node.gateway.NodeGateway;
import io.graxon.library.node.NodeStatus;
import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 */
@UseCase
class DownloadKeystoreUseCaseImpl implements DownloadKeystoreUseCase {
    // --------------------------------------------------------------------------------------------

    //
    private final Logger log = LoggerFactory.getLogger(DownloadKeystoreUseCaseImpl.class);

    //
    private final ClusterGateway clusterGateway;
    private final NodeGateway nodeGateway;

    /**
     * Constructor
     *
     * @param clusterGateway cluster gateway
     * @param nodeGateway    node gateway
     */
    DownloadKeystoreUseCaseImpl(ClusterGateway clusterGateway, NodeGateway nodeGateway) {
        this.clusterGateway = clusterGateway;
        this.nodeGateway = nodeGateway;
    }

    /**
     * Download the keystore for the given cluster
     *
     * @param clusterId the cluster id
     * @throws ClusterPendingStateException if the cluster is still pending
     */
    @Override
    public Resource execute(String clusterId, String nodeId) throws ClusterPendingStateException {
        log.debug("downloading keystore for cluster: {}", clusterId);

        // getting all clusters that are not deleted
        var cluster = clusterGateway.findById(clusterId)
            .filter(c -> !ClusterStatus.DELETED.equals(c.status()))
            .orElseThrow(ClusterNotFoundException::new);

        //
        var node = nodeGateway.findById(nodeId)
            .filter(n -> !NodeStatus.DELETED.equals(n.status()))
            .filter(n -> cluster.id().equals(n.clusterId()))
            .orElseThrow(NodeNotFoundException::new);

        //
        var updatedNode = new Node(
            node.id(),
            NodeStatus.ACTIVE,
            node.createdAt(),
            LocalDateTime.now(),
            node.clusterId()
        );
        nodeGateway.update(updatedNode).orElseThrow(NodeNotUpdatedException::new);

        // TODO: update last seen time

        // check if the cluster is still pending
        if (!ClusterStatus.ACTIVE.equals(cluster.status()) && !ClusterStatus.INACTIVE.equals(cluster.status())) {
            throw new ClusterPendingStateException();
        }

        // download the keystore
        return downloadKeystore(clusterId)
            .orElseThrow(ClusterKeystoreNotFoundException::new);
    }

    /**
     * Download the keystore
     *
     * @param clusterId the cluster id
     * @return the keystore
     */
    private Optional<Resource> downloadKeystore(String clusterId) {
        try {

            //
            var dirPath = Paths.get("keystores");
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            //
            File keystoreFile;
            try (Stream<Path> entries = Files.walk(dirPath, 1)) {
                keystoreFile = entries
                    .filter(f -> f.getFileName().toString().equals(clusterId + ".p12"))
                    .map(Path::toFile)
                    .findAny()
                    .orElseThrow();
            }
            return Optional.of(new UrlResource(keystoreFile.toURI()));
        } catch (Exception e) {
            log.warn("can't find keystore file: {}", e.getMessage());
        }
        return Optional.empty();
    }
}
