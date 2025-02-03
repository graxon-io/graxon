package io.graxon.core.node.gateway;

import io.graxon.core.entities.node.Node;
import io.graxon.core.entities.node.gateway.NodeGateway;
import io.graxon.core.node.datasource.NodeMapper;
import io.graxon.core.node.datasource.NodeRepository;
import io.graxon.library.system.annotations.Gateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Gateway
class NodeGatewayImpl implements NodeGateway {
    // --------------------------------------------------------------------------------------------

    //
    private static final Logger log = LoggerFactory.getLogger(NodeGatewayImpl.class);

    //
    private final NodeRepository repository;

    /**
     *
     */
    public NodeGatewayImpl(NodeRepository repository) {
        this.repository = repository;
    }

    /**
     *
     */
    @Override
    public List<Node> findAll() {
        log.debug("finding all nodes");
        return repository.findAll().stream().map(NodeMapper::toEntity).toList();
    }

    /**
     * @param id node id
     */
    @Override
    public Optional<Node> findById(String id) {
        log.debug("finding node by id: {}", id);
        return repository.findById(id).map(NodeMapper::toEntity);
    }

    /**
     * find node by cluster
     *
     * @param clusterId cluster id
     *
     * @return list of nodes
     */
    @Override
    public List<Node> findByCluster(String clusterId) {
        log.debug("finding node by cluster: {}", clusterId);
        return repository.findByCluster(clusterId).stream().map(NodeMapper::toEntity).toList();
    }

    /**
     * save node
     *
     * @param node node
     * @return node
     */
    @Override
    public Optional<Node> save(Node node) {
        log.debug("saving node: {}", node);
        return repository.save(NodeMapper.toSchema(node)).map(NodeMapper::toEntity);
    }

    /**
     * update node
     *
     * @param node node
     * @return node
     */
    @Override
    public Optional<Node> update(Node node) {
        log.debug("updating node: {}", node);
        return repository.update(NodeMapper.toSchema(node)).map(NodeMapper::toEntity);
    }
}
