package io.graxon.core.project.events;

import io.graxon.library.events.ProjectStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static io.graxon.library.system.constants.RabbitMQConstants.EXCHANGE_PROJECT;

/**
 *
 */
@Profile("events")
@Repository
public class ProjectPublisherRabbitMQ implements ProjectPublisher {
    // ---------------------------------------------------------------------------------

    //
    private final Logger log = LoggerFactory.getLogger(getClass());

    //
    private final RabbitTemplate template;

    /**
     */
    public ProjectPublisherRabbitMQ(RabbitTemplate template) {
        this.template = template;
    }

    /**
     */
    @Override
    public void publishProjectStatusEvent(ProjectStatusEvent event) {
        log.trace("publish: {}", event);
        template.convertAndSend(EXCHANGE_PROJECT, "", event);
    }

}
