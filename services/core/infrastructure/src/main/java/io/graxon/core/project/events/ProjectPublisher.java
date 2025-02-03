package io.graxon.core.project.events;

import io.graxon.library.events.ProjectStatusEvent;

/**
 *
 */
public interface ProjectPublisher {

    void publishProjectStatusEvent(ProjectStatusEvent event);

}
