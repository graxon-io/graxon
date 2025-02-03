package io.graxon.core.entities.node.gateway;

import io.graxon.core.entities.node.Node;
import io.graxon.library.node.NodeRegistrationRequest;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface NodeGateway {

    /**
     * fetch all nodes
     *
     * @return list of nodes
     */
    List<Node> findAll();

    /**
     * find node by id
     *
     * @param id node id
     * @return node
     */
    Optional<Node> findById(String id);

    /**
     * find node by cluster
     *
     * @param clusterId cluster id
     * @return list of nodes
     */
    List<Node> findByCluster(String clusterId);

    /**
     * save a node
     *
     * @param node node
     * @return saved node
     */
    Optional<Node> save(Node node);

    /**
     * update a node
     *
     * @param node node
     * @return updated node
     */
    Optional<Node> update(Node node);
}
