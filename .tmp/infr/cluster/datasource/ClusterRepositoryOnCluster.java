package io.graxon.core.cluster.datasource;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.graxon.core.cluster.schema.ClusterCRD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Profile("kubernetes")
@Repository
public class ClusterRepositoryOnCluster implements ClusterRepository {

    //
    private static final Logger log = LoggerFactory.getLogger(ClusterRepositoryOnCluster.class);

    //
    private final KubernetesClient kubernetes;

    /**
     *
     */
    public ClusterRepositoryOnCluster(KubernetesClient kubernetes) {
        this.kubernetes = kubernetes;
    }

    /**
     * fetch all clusters
     *
     * @return list of clusters
     */
    @Override
    public List<ClusterCRD> findAll() {
        log.trace("find all clusters");
        return kubernetes.resources(ClusterCRD.class).list().getItems();
    }

    /**
     * find cluster by id
     *
     * @param id cluster id
     * @return cluster
     */
    @Override
    public Optional<ClusterCRD> findById(String id) {
        log.trace("find cluster by id: {}", id);
        try {
            return Optional.of(kubernetes.resources(ClusterCRD.class).withName(id).get());
        } catch (Exception e) {
            log.trace("cluster {} not found -> {}", id, e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * save cluster
     *
     * @param cluster cluster
     * @return saved cluster
     */
    @Override
    public Optional<ClusterCRD> save(ClusterCRD cluster) {
        log.trace("saving cluster: {}", cluster);
        try {
            kubernetes.resources(ClusterCRD.class).resource(cluster).create();
            return Optional.of(kubernetes.resources(ClusterCRD.class).resource(cluster).updateStatus());
        } catch (Exception e) {
            log.trace("cluster not created -> {}", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * update cluster
     *
     * @param cluster cluster
     * @return updated cluster
     */
    @Override
    public Optional<ClusterCRD> update(ClusterCRD cluster) {
        log.trace("updating cluster: {}", cluster);
        try {
            kubernetes.resources(ClusterCRD.class).resource(cluster).update();
            return Optional.of(kubernetes.resources(ClusterCRD.class).resource(cluster).updateStatus());
        } catch (Exception e) {
            log.trace("cluster not updated -> {}", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * delete cluster
     *
     * @param cluster cluster
     */
    @Override
    public void delete(ClusterCRD cluster) {
        log.info("Deleting cluster: {}", cluster);
        try {
            kubernetes.resources(ClusterCRD.class).resource(cluster).delete();
        } catch (Exception e) {
            log.trace("cluster not deleted -> {}", e.getMessage());
        }
    }

}
