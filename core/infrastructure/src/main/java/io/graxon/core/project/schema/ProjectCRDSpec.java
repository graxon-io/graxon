package io.graxon.core.project.schema;

/**
 * Project entity.
 *
 * @param name        Name of the project
 * @param description Description of the project
 */
public record ProjectCRDSpec(
    String name,
    String description
) {
}
