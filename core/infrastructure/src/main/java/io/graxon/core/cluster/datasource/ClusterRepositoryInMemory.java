package io.graxon.core.cluster.datasource;

import io.graxon.core.cluster.schema.ClusterCRD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@Profile("!kubernetes")
@Repository
public class ClusterRepositoryInMemory implements ClusterRepository {

    //
    private static final Logger log = LoggerFactory.getLogger(ClusterRepositoryInMemory.class);

    //
    private final Map<String, ClusterCRD> database = new HashMap<>();

    /**
     * fetch all clusters
     *
     * @return list of clusters
     */
    @Override
    public List<ClusterCRD> findAll() {
        log.trace("find all clusters");
        return database.values().stream().toList();
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
        return Optional.ofNullable(database.get(id));
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
        database.put(cluster.getMetadata().getName(), cluster);
        return Optional.of(cluster);
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
        database.put(cluster.getMetadata().getName(), cluster);
        return Optional.of(cluster);
    }

    /**
     * delete cluster
     *
     * @param cluster cluster
     */
    @Override
    public void delete(ClusterCRD cluster) {
        log.info("Deleting cluster: {}", cluster);
        database.remove(cluster.getMetadata().getName());
    }

}
