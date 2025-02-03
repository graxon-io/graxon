package io.graxon.core.cluster.datasource;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.graxon.core.entities.cluster.Cluster;
import io.graxon.core.entities.cluster.ClusterStatus;
import io.graxon.core.cluster.schema.ClusterCRD;
import io.graxon.core.cluster.schema.ClusterCRDSpec;
import io.graxon.core.cluster.schema.ClusterCRDStatus;

import java.util.Map;

import static io.graxon.core.system.configs.KubernetesClientConfig.*;


/**
 *
 */
public abstract class ClusterMapper {

    /**
     *
     */
    public static Cluster toEntity(ClusterCRD schema) {
        return new Cluster(
            schema.getMetadata().getName(),
            schema.getMetadata().getName(),
            schema.getStatus() != null ? schema.getStatus().url() : null,
            schema.getStatus() != null ? schema.getStatus().status() : ClusterStatus.UNKNOWN,
            schema.getStatus() != null ? schema.getStatus().createdAt() : null,
            schema.getStatus() != null ? schema.getStatus().updatedAt() : null
        );
    }

    /**
     *
     */
    public static ClusterCRD toSchema(Cluster entity) {

        //
        var meta = new ObjectMeta();
        meta.setName(entity.id());
        meta.setLabels(Map.of(
            LABEL_MANAGED_BY, LABEL_MANAGED_BY_VALUE
        ));

        //
        var spec = new ClusterCRDSpec();

        //
        var status = new ClusterCRDStatus(
            entity.url(),
            entity.status(),
            entity.createdAt(),
            entity.updatedAt()
        );

        //
        var schema = new ClusterCRD();
        schema.setMetadata(meta);
        schema.setSpec(spec);
        schema.setStatus(status);

        //
        return schema;
    }
}
