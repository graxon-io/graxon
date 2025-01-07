package io.graxon.core.cluster.datasource;

import io.graxon.core.cluster.schema.ClusterCRD;

import java.util.List;
import java.util.Optional;

public interface ClusterRepository {

    List<ClusterCRD> findAll();

    Optional<ClusterCRD> findById(String id);

    Optional<ClusterCRD> save(ClusterCRD cluster);

    Optional<ClusterCRD> update(ClusterCRD cluster);

    void delete(ClusterCRD cluster);

}
