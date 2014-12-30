package com.dit;

import com.dit.account.User;

import java.util.Map;

public class Notification {

    public static enum Type {
        EMAIL, SMS
    }
    private Type type;

    private NotificationUser user;

    private Map<String, String> data;

    public NotificationUser getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = buildUser(user);
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public NotificationUser buildUser(User u) {
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setName(u.getPerson().getFullName());
        notificationUser.setEmail(u.getEmail());
        notificationUser.setPhone(u.getPerson().getContactNumber());
        return notificationUser;
    }

    class NotificationUser {

        private String name;

        private String email;

        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

    }
}
