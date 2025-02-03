package io.graxon.core.usecases.project.dto;

import io.graxon.core.entities.project.Project;
import io.graxon.library.enums.ProjectStatus;

/**
 *
 * @param id
 * @param name
 * @param description
 */
public record ProjectDTO(
    String id,
    String name,
    String description,
    ProjectStatus status
) {
    /**
     * Constructor
     * @param entity project entity
     */
    public ProjectDTO(Project entity) {
        this(
            entity.id(),
            entity.name(),
            entity.description(),
            entity.status());
    }
}
