package com.dit.engine.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:engine-context.xml"})
public class EventConsumerTest {

    @Autowired
    private EventConsumer eventConsumer;

    @Test
    public void testReceiveMessage() throws Exception {
        String user = "{\"id\":\"54a183145194baced32b6b3d\",\"username\":\"rajith_delantha@yahoo.com\",\"email\":\"rajith_delantha@yahoo.com\",\"person\":{\"firstName\":\"Rajith\",\"lastName\":\"Delantha\"},\"userConnection\":{\"providerId\":\"FACEBOOK\",\"displayName\":\"Rajith Delantha\"}}";
        eventConsumer.receiveMessage(user);

    }
}