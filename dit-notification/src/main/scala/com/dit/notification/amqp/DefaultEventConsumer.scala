package com.dit.notification.amqp

import akka.actor.{Props, ActorSystem}
import com.dit.notification.event.{NotificationEventHandler, Notification}
import com.rabbitmq.client._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.slf4j.LoggerFactory


class DefaultEventConsumer(val ch: Channel, val actorSystem: ActorSystem) extends DefaultConsumer(ch) {

  private val log = LoggerFactory getLogger this.getClass

  implicit val formats = DefaultFormats


  override def handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: Array[Byte]) = {
    log info "========== Message Received =============="
    val message = new String(body)
    log info ("Received message: {}", message)
    parseOpt(message, useBigDecimalForDouble = true).map{ rawJson =>
      val transformedJson = rawJson transformField {
        case ("type",x) => ("notificationType", x)
      }
      val event = transformedJson.extract[Notification]
      val eventHandler = actorSystem.actorOf(Props[NotificationEventHandler])
      eventHandler ! event
    }

  }
}
