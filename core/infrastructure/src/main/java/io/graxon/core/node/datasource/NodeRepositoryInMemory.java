package io.graxon.core.node.datasource;

import io.graxon.core.node.schema.NodeCRD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.graxon.core.system.configs.KubernetesClientConfig.*;

/**
 *
 */
@Profile("!kubernetes")
@Repository
public class NodeRepositoryInMemory implements NodeRepository {

    //
    private static final Logger log = LoggerFactory.getLogger(NodeRepositoryInMemory.class);

    //
    private final Map<String, NodeCRD> database = new HashMap<>();

    /**
     * fetch all nodes
     *
     * @return list of nodes
     */
    @Override
    public List<NodeCRD> findAll() {
        log.trace("find all nodes");
        return database.values().stream().toList();
    }

    /**
     * find node by id
     *
     * @param id node id
     * @return node
     */
    @Override
    public Optional<NodeCRD> findById(String id) {
        log.trace("find node by id: {}", id);
        return Optional.ofNullable(database.get(id));
    }

    /**
     * find node by cluster
     * @param clusterId cluster id
     * @return list of nodes
     */
    @Override
    public List<NodeCRD> findByCluster(String clusterId) {
        return database.values().stream()
            .filter(n -> n.getMetadata().getLabels() != null)
            .filter(n -> n.getMetadata().getLabels().get(LABEL_MANAGED_BY).equals(LABEL_MANAGED_BY_VALUE))
            .filter(n -> n.getMetadata().getLabels().containsKey(LABEL_GRAXON_CLUSTER))
            .filter(n -> clusterId.equals(n.getMetadata().getLabels().get(LABEL_GRAXON_CLUSTER)))
            .toList();
    }

    /**
     * save node
     *
     * @param node node
     * @return saved node
     */
    @Override
    public Optional<NodeCRD> save(NodeCRD node) {
        log.trace("saving node: {}", node);
        database.put(node.getMetadata().getName(), node);
        return Optional.of(node);
    }

    /**
     * update node
     *
     * @param node node
     * @return updated node
     */
    @Override
    public Optional<NodeCRD> update(NodeCRD node) {
        log.trace("updating node: {}", node);
        database.put(node.getMetadata().getName(), node);
        return Optional.of(node);
    }

    /**
     * delete node
     *
     * @param node node
     */
    @Override
    public void delete(NodeCRD node) {
        log.info("Deleting node: {}", node);
        database.remove(node.getMetadata().getName());
    }

}
