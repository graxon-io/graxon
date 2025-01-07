package io.graxon.core.project.events;

import io.graxon.library.events.ProjectStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Profile("!events")
@Repository
public class ProjectPublisherConsole implements ProjectPublisher {

    //
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     *
     */
    @Override
    public void publishProjectStatusEvent(ProjectStatusEvent event) {
        log.trace("publishing project status event: {}", event);
    }
}
