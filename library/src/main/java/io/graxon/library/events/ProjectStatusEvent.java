package io.graxon.library.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.graxon.library.enums.ProjectStatus;

/**
 * Event that is published when the status of a project changes.
 *
 * @param projectId     The id of the project
 * @param projectName   The name of the project
 * @param projectStatus The new status of the project
 */
public record ProjectStatusEvent(

    @JsonProperty("project_id")
    String projectId,

    @JsonProperty("project_name")
    String projectName,

    @JsonProperty("project_status")
    ProjectStatus projectStatus

) {
}
