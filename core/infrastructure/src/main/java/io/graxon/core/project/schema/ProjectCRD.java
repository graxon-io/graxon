package io.graxon.core.project.schema;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.*;

/**
 *
 */
@Group("graxon.io")
@Version("v1")
@Kind("Project")
@Singular("project")
@Plural("projects")
public class ProjectCRD extends CustomResource<ProjectCRDSpec, ProjectCRDStatus> implements Namespaced {

}
