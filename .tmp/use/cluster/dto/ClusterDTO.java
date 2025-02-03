package io.graxon.core.usecases.cluster.dto;

import io.graxon.core.entities.cluster.Cluster;
import io.graxon.core.entities.cluster.ClusterStatus;
import io.graxon.core.usecases.node.dto.NodeDTO;

import java.util.List;

/**
 *
 * @param id
 * @param name
 * @param status
 */
public record ClusterDTO(
    String id,
    String name,
    String url,
    ClusterStatus status,
    List<NodeDTO> nodes
) {

    /**
     *
     */
    public ClusterDTO(Cluster cluster) {
        this(
            cluster.id(),
            cluster.name(),
            cluster.url(),
            cluster.status(),
            null);
    }

    /**
     *
     * @param cluster
     * @param nodes
     */
    public ClusterDTO(Cluster cluster, List<NodeDTO> nodes) {
        this(
            cluster.id(),
            cluster.name(),
            cluster.url(),
            cluster.status(),
            nodes);
    }
}
