package controllers

import play.api._
import play.api.mvc._
import views._
import models._
import controllers.Actions._
import com.mongodb.casbah.WriteConcern
import se.radley.plugin.salat._
import com.mongodb.casbah.Imports._
import com.novus.salat._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import Actions._
import play.api.libs.json._

object Application extends Controller {
  //val objectId = of[ObjectId]
  val moviesForm = Form(
    mapping(
      "_id" ->  ignored(new ObjectId()),
      //"_id" ->  optional(objectId),
      "title" -> text,
      "genre" -> text,
      "description" -> text,
      "release_date" -> optional(date("dd/MM/yyyy")),
      "director" -> text,
      "writer" -> text,
      "actors" -> text
    )(Movie.apply)(Movie.unapply)
  )

  def list() = Action {
      val movies = Movie.findAll
      Ok(html.list(movies))
  }

  def view(id: ObjectId) = Action {
      Movie.findOneById(id).map( movie =>
        Ok(html.movie(movie))
      ).getOrElse(NotFound)
  }

  def add() = Action {
    Ok(html.add(moviesForm))
  }

  def addMovie = Action {
    implicit request =>
      def values = moviesForm.bindFromRequest()(request).data

    val movie = Movie(
        title = values("title"),
        genre = values("genre"),
        description = values("description"),
        director = values("director"),
        writer = values("writer"),
        actors = values("actors")
      )
      Movie.saveMovie(movie)
      val movies = Movie.findAll
      Ok(html.list(movies))
  }

  def removeMovie(id: ObjectId) = Action {
    Movie.removeById(id)
    val movies = Movie.findAll
    Ok(html.list(movies))
  }

  def updateMovie(id: ObjectId) = Action {
    val movie = Movie(
      id = id,
      title = "newTitle",
      genre = "newGenre",
      description = "new Description",
      director = "newDirector",
      writer =  "newWriter",
      actors = "newActors"
    )
    Movie.updateById(movie)
    Ok("updated!")
  }
}