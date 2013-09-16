package controllers

import play.api._
import play.api.mvc._
import views._
import models._
import se.radley.plugin.salat._
import com.mongodb.casbah.Imports._
import com.novus.salat._

object Application extends Controller {

  def list() = Action {
      val movies = Movie.findAll
      Ok(html.list(movies))
  }

  def view(id: ObjectId) = Action {
      Movie.findOneById(id).map( movie =>
        Ok(html.movie(movie))
      ).getOrElse(NotFound)
  }
}