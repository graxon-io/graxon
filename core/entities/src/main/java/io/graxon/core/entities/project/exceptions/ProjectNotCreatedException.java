package io.graxon.core.entities.project.exceptions;

import io.graxon.library.system.exceptions.EntityNotCreatedException;

/**
 * Exception thrown when a project is not created.
 */
public class ProjectNotCreatedException extends EntityNotCreatedException {

    /**
     * Constructor with message.
     */
    public ProjectNotCreatedException() {
        super("Project not created");
    }

}
