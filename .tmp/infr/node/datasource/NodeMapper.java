package io.graxon.core.node.datasource;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.graxon.core.entities.node.Node;
import io.graxon.core.node.schema.NodeCRD;
import io.graxon.core.node.schema.NodeCRDSpec;
import io.graxon.core.node.schema.NodeCRDStatus;
import io.graxon.library.node.NodeStatus;

import java.util.Map;

import static io.graxon.core.system.configs.KubernetesClientConfig.*;


/**
 *
 */
public abstract class NodeMapper {

    /**
     * Convert schema to entity
     *
     * @param schema schema
     * @return entity
     */
    public static Node toEntity(NodeCRD schema) {
        return new Node(
            schema.getMetadata().getName(),
            schema.getStatus() != null ? schema.getStatus().status() : NodeStatus.UNKNOWN,
            schema.getStatus() != null ? schema.getStatus().createdAt() : null,
            schema.getStatus() != null ? schema.getStatus().updatedAt() : null,
            schema.getMetadata().getLabels().get(LABEL_GRAXON_CLUSTER)
        );
    }

    /**
     * Convert entity to schema
     *
     * @param entity entity
     * @return schema
     */
    public static NodeCRD toSchema(Node entity) {

        //
        var meta = new ObjectMeta();
        meta.setName(entity.id());
        meta.setLabels(Map.of(
            LABEL_MANAGED_BY, LABEL_MANAGED_BY_VALUE,
            LABEL_GRAXON_CLUSTER, entity.clusterId()
        ));

        //
        var spec = new NodeCRDSpec();

        //
        var status = new NodeCRDStatus(
            entity.status(),
            entity.createdAt(),
            entity.updatedAt());

        //
        var schema = new NodeCRD();
        schema.setMetadata(meta);
        schema.setSpec(spec);
        schema.setStatus(status);

        //
        return schema;
    }
}
