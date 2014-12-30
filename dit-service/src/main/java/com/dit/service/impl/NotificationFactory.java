package com.dit.service.impl;

import com.dit.Notification;
import com.dit.account.User;

import java.util.Map;

public class NotificationFactory {

    public static Notification buildNotification(User user, Map<String, String> data) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(Notification.Type.EMAIL);
        notification.setData(data);
        return notification;
    }


}
