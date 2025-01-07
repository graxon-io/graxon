package io.graxon.core.system.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static io.graxon.library.system.constants.RabbitMQConstants.EXCHANGE_PROJECT;

/**
 *
 */
@Profile(value = "events")
@Configuration
public class RabbitMQConfig {
    // ---------------------------------------------------------------------------------

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
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setObservationEnabled(true);
        return rabbitTemplate;
    }

    /**
     *
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
