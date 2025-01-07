package io.graxon.core.node.datasource;

import io.graxon.core.node.schema.NodeCRD;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface NodeRepository {

    List<NodeCRD> findAll();

    Optional<NodeCRD> findById(String id);

    List<NodeCRD> findByCluster(String clusterId);

    Optional<NodeCRD> save(NodeCRD node);

    Optional<NodeCRD> update(NodeCRD node);

    void delete(NodeCRD node);

}
