package com.dit.service.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitGateway {



    @Autowired
    @Qualifier("direct.notification")
    private RabbitTemplate rabbitTemplate;

    public void publish(String message, String key) {
        rabbitTemplate.convertAndSend(key, message);
    }

}
