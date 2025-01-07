package io.graxon.core.entities.cluster.gateway;

import io.graxon.core.entities.cluster.Cluster;

import java.util.List;
import java.util.Optional;

/**
 * 
 */
public interface ClusterGateway {

    /**
     * fetch all clusters
     *
     * @return list of clusters
     */
    List<Cluster> findAll();

    /**
     * find cluster by id
     *
     * @param id cluster id
     * @return cluster
     */
    Optional<Cluster> findById(String id);

    /**
     * save cluster
     *
     * @param cluster cluster
     * @return saved cluster
     */
    Optional<Cluster> save(Cluster cluster);

    /**
     * update cluster
     *
     * @param cluster cluster
     * @return saved cluster
     */
    Optional<Cluster> update(Cluster cluster);

    /**
     * delete cluster
     *
     * @param cluster cluster
     */
    void delete(Cluster cluster);

}
