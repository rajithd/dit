package com.dit.service.impl;

import com.dit.service.NotificationService;
import com.dit.service.amqp.RabbitGateway;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RabbitGateway rabbitGateway;

    @Override
    public void publishMessage(Object object, String key) {
        rabbitGateway.publish(new Gson().toJson(object), key);
    }
}
