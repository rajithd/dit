package com.dit.notification

import akka.actor.{ActorSystem, Props}
import com.dit.notification.amqp.DefaultEventConsumer
import com.dit.notification.event.EventTypes
import com.dit.notification.util.ConfigSupport
import com.rabbitmq.client.{DefaultConsumer, ConnectionFactory, Connection}
import org.slf4j.LoggerFactory

object Application extends ConfigSupport{

  private val log = LoggerFactory getLogger this.getClass

  //    Setup the ActorSystem
  val actorSystem = ActorSystem("DIT-NOTIFICATION")

  def main(args: Array[String]):Unit = {
    log info "=================================================================="
    log info "Starting DIT Notification Service"
    log info ("AMQP Broker: amqp://{}:{}@{}:{}/", amqpBrokerUsername, amqpBrokerPassword, amqpBrokerHost, amqpBrokerPort.toString)
    log info "=================================================================="

//    Setup the AMQP Connection Factory:
    val connectionFactory = new ConnectionFactory()
    connectionFactory setUsername amqpBrokerUsername
    connectionFactory setPassword amqpBrokerPassword
    connectionFactory setHost amqpBrokerHost
    connectionFactory setPort amqpBrokerPort

    val connection = connectionFactory.newConnection()
    setupChannel(connection, emailNotificationQueue, "registerEmailNotification")
  }

  def setupChannel(conn: Connection, queueName: String, eventType: String) = {
    val channel = conn.createChannel()
    val consumer = new DefaultEventConsumer(actorSystem = actorSystem, ch=channel)
    channel.queueDeclare(queueName, true, false, false, null)
    channel.queueBind(queueName, exchangeName, eventType)
    channel.basicConsume(queueName, true, consumer)
    channel
  }
}
