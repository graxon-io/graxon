package io.graxon.core.entities.project;

import io.graxon.library.enums.ProjectStatus;

/**
 * Project entity
 *
 * @param id          Unique identifier
 * @param name        Name of the project
 * @param description Project version
 * @param status      Project status
 */
public record Project(
    String id,
    String name,
    String description,
    ProjectStatus status
) {
}
