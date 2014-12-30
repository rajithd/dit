package com.dit.service.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitGateway {

    private static final String NOTIFICATION_KEY = "registerEmailNotification";

    @Autowired
    @Qualifier("direct.notification")
    private RabbitTemplate rabbitTemplate;

    public void publish(String message) {
        rabbitTemplate.convertAndSend(NOTIFICATION_KEY, message);
    }

}
