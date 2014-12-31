package com.dit.notification.event

import java.util.Properties
import javax.mail.{PasswordAuthentication, Session}

import akka.actor.{Props, Actor}
import org.slf4j.LoggerFactory
import org.stringtemplate.v4._


class NotificationEventHandler extends Actor {

  override def receive: Actor.Receive = {
    case evt: Notification => handleEvent(evt)
  }

  def handleEvent(e: Notification) = {
    handleEmailRequest(e)
  }

  def handleEmailRequest(e: Notification) = context.actorOf(Props[EmailEventHandler]) forward e

}

trait EmailSender extends Actor {
  override def receive: Receive = {
    case event: Notification => sendMessage(event)
  }

  def sendMessage(e: Notification): Unit
}

class EmailEventHandler extends EmailSender {

  private val log = LoggerFactory getLogger this.getClass

  def sendMessage(e: Notification) = {
    log info "======================>>"

    val template = new ST(io.Source.fromInputStream(getClass.getResourceAsStream("/templates/registerEmail.txt")).mkString)
    template.add("name", e.user.name)



    log info "======================>> " + template.render()
  }
}
