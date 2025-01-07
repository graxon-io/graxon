package io.graxon.core.node.datasource;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.graxon.core.node.schema.NodeCRD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static io.graxon.core.system.configs.KubernetesClientConfig.*;

/**
 *
 */
@Profile("kubernetes")
@Repository
public class NodeRepositoryOnCluster implements NodeRepository {

    //
    private static final Logger log = LoggerFactory.getLogger(NodeRepositoryOnCluster.class);

    //
    private final KubernetesClient kubernetes;

    /**
     *
     */
    public NodeRepositoryOnCluster(KubernetesClient kubernetes) {
        this.kubernetes = kubernetes;
    }

    /**
     * fetch all nodes
     *
     * @return list of nodes
     */
    @Override
    public List<NodeCRD> findAll() {
        log.trace("find all nodes");
        return kubernetes.resources(NodeCRD.class).list().getItems();
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
        try {
            return Optional.of(kubernetes.resources(NodeCRD.class).withName(id).get());
        } catch (Exception e) {
            log.trace("node {} not found -> {}", id, e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * find node by cluster
     * @param clusterId cluster id
     * @return list of nodes
     */
    @Override
    public List<NodeCRD> findByCluster(String clusterId) {
        return kubernetes.resources(NodeCRD.class).list().getItems().stream()
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
        try {
            kubernetes.resources(NodeCRD.class).resource(node).create();
            return Optional.of(kubernetes.resources(NodeCRD.class).resource(node).updateStatus());
        } catch (Exception e) {
            log.trace("node not created -> {}", e.getMessage());
        }
        return Optional.empty();
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
        try {
            kubernetes.resources(NodeCRD.class).resource(node).update();
            return Optional.of(kubernetes.resources(NodeCRD.class).resource(node).updateStatus());
        } catch (Exception e) {
            log.trace("node not updated -> {}", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * delete node
     *
     * @param node node
     */
    @Override
    public void delete(NodeCRD node) {
        log.info("Deleting node: {}", node);
        try {
            kubernetes.resources(NodeCRD.class).resource(node).delete();
        } catch (Exception e) {
            log.trace("node not deleted -> {}", e.getMessage());
        }
    }

}
