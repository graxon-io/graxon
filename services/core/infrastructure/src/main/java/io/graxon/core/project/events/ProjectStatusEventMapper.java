package io.graxon.core.project.events;

import io.graxon.core.entities.project.Project;
import io.graxon.library.events.ProjectStatusEvent;

/**
 *
 */
public abstract class ProjectStatusEventMapper {

    /**
     *
     */
    public static ProjectStatusEvent toEvent(Project entity) {
        return new ProjectStatusEvent(
            entity.id(),
            entity.name(),
            entity.status()
        );
    }

}
