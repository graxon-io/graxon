package io.graxon.core.project.schema;

import io.graxon.library.enums.ProjectStatus;

/**
 * Project entity.
 *
 * @param status Status of the project
 *
 */
public record ProjectCRDStatus(
    ProjectStatus status
) {
}
