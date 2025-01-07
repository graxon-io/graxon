package io.graxon.core.cluster.gateway;

import io.graxon.core.cluster.datasource.ClusterMapper;
import io.graxon.core.cluster.datasource.ClusterRepository;
import io.graxon.core.entities.cluster.Cluster;
import io.graxon.core.entities.cluster.gateway.ClusterGateway;
import io.graxon.library.system.annotations.Gateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Gateway
class ClusterGatewayImpl implements ClusterGateway {

    //
    private static final Logger log = LoggerFactory.getLogger(ClusterGatewayImpl.class);

    //
    private final ClusterRepository repository;

    /**
     * Constructor
     *
     * @param repository cluster repository
     */
    ClusterGatewayImpl(ClusterRepository repository) {
        this.repository = repository;
    }

    /**
     * @return
     */
    @Override
    public List<Cluster> findAll() {
        log.debug("find all clusters");
        return repository.findAll().stream().map(ClusterMapper::toEntity).toList();
    }

    /**
     * @param id cluster id
     * @return
     */
    @Override
    public Optional<Cluster> findById(String id) {
        log.debug("find cluster by id: {}", id);
        return repository.findById(id).map(ClusterMapper::toEntity);
    }

    /**
     * @param cluster cluster
     * @return
     */
    @Override
    public Optional<Cluster> save(Cluster cluster) {
        log.debug("save cluster: {}", cluster);
        return repository.save(ClusterMapper.toSchema(cluster)).map(ClusterMapper::toEntity);
    }

    /**
     *
     * @param cluster cluster
     * @return
     */
    @Override
    public Optional<Cluster> update(Cluster cluster) {
        log.debug("update cluster: {}", cluster);
        return repository.update(ClusterMapper.toSchema(cluster)).map(ClusterMapper::toEntity);
    }

    /**
     * @param cluster cluster
     */
    @Override
    public void delete(Cluster cluster) {
        log.debug("delete cluster: {}", cluster);
        repository.delete(ClusterMapper.toSchema(cluster));
    }
}
