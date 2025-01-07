package io.graxon.core.project.datasource;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.graxon.core.entities.project.Project;
import io.graxon.core.project.schema.ProjectCRD;
import io.graxon.core.project.schema.ProjectCRDSpec;
import io.graxon.core.project.schema.ProjectCRDStatus;
import io.graxon.library.enums.ProjectStatus;

import java.util.Map;

import static io.graxon.core.system.configs.KubernetesClientConfig.LABEL_MANAGED_BY;
import static io.graxon.core.system.configs.KubernetesClientConfig.LABEL_MANAGED_BY_VALUE;


/**
 *
 */
public abstract class ProjectMapper {

    /**
     *
     */
    public static Project toEntity(ProjectCRD schema) {
        return new Project(
            schema.getMetadata().getName(),
            schema.getSpec().name(),
            schema.getSpec().description(),
            schema.getStatus() != null ? schema.getStatus().status() : ProjectStatus.UNKNOWN
        );
    }

    /**
     *
     */
    public static ProjectCRD toSchema(Project entity) {

        //
        var meta = new ObjectMeta();
        meta.setName(entity.id());
        meta.setLabels(Map.of(
            LABEL_MANAGED_BY, LABEL_MANAGED_BY_VALUE
        ));

        //
        var spec = new ProjectCRDSpec(
            entity.name(),
            entity.description());

        //
        var status = new ProjectCRDStatus(
            entity.status());

        //
        var schema = new ProjectCRD();
        schema.setMetadata(meta);
        schema.setSpec(spec);
        schema.setStatus(status);

        //
        return schema;
    }
}
