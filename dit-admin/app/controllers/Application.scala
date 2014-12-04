package controllers

import model.Login
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def login = Action {
    Ok(views.html.login())
  }


  val loginForm: Form[Login] = Form {
    mapping(
      "username" -> text,
      "password" -> text
    )(Login.apply)(Login.unapply)
  }


  def submit = Action { implicit request =>
    val login = loginForm.bindFromRequest.get
    println(login.username)
    println(login.password)
    Redirect(routes.Application.index)
  }
}