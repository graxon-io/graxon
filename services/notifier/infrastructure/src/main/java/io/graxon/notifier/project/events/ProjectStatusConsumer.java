package io.graxon.notifier.project.events;

import io.graxon.library.events.ProjectStatusEvent;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static io.graxon.library.system.constants.RabbitMQConstants.EXCHANGE_PROJECT;

/**
 *
 */
@Profile(value = "events")
@Component
public class ProjectStatusConsumer {

    //
    public static final String QUEUE_PROJECT_STATUS = "notifier-project-status";

    /**
     *
     */
    @Bean
    public FanoutExchange topicExchange() {
        return new FanoutExchange(EXCHANGE_PROJECT);
    }

    /**
     *
     */
    @Bean
    public Queue queueProjectStatus() {
        return new Queue(QUEUE_PROJECT_STATUS, false);
    }

    /**
     *
     */
    @Bean
    public Binding bindingBravoFanout(@Qualifier("queueProjectStatus") Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     *
     */
    @RabbitListener(queues = QUEUE_PROJECT_STATUS)
    public void listen(ProjectStatusEvent event) {
        System.out.println("Received message: " + event);
    }

}
