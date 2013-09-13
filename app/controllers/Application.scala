package controllers

import play.api._
import play.api.mvc._
import views._
import models._

object Application extends Controller {

  def list() = Action {
      val movies = Movie.findAll
      Ok(html.list(movies))
  }
}