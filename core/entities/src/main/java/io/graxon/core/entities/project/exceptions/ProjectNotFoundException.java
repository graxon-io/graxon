package io.graxon.core.entities.project.exceptions;

import io.graxon.library.system.exceptions.EntityNotFoundException;

/**
 * Exception thrown when a project is not found.
 */
public class ProjectNotFoundException extends EntityNotFoundException {

    /**
     * Constructor with message.
     */
    public ProjectNotFoundException() {
        super("Project not found");
    }

}
