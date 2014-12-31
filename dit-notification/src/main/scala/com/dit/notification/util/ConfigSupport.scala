package com.dit.notification.util

import com.typesafe.config.ConfigFactory

trait ConfigSupport {
  val config = ConfigFactory.load()

  val amqpBrokerHost = config getString "amqp.host"
  val amqpBrokerPort = config getInt "amqp.port"
  val amqpBrokerUsername = config getString "amqp.user"
  val amqpBrokerPassword = config getString "amqp.pass"
  val exchangeName = config getString "amqp.exchange"
  
  val emailNotificationQueue = config getString "amqp.queues.emailNotification"

}
