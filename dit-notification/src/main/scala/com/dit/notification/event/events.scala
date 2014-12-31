package com.dit.notification.event

case class NotificationUser(name: String, email: String, phone: Option[String])
case class Notification(notificationType: String, user : NotificationUser, data : Option[Map[String,String]])