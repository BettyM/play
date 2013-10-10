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
      //"_id" ->  ignored(new ObjectId()),
      "_id" ->  optional(objectId),
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


  /*def addMovie = JsonAction[Movie] { movie =>
    Movie.insert(movie, WriteConcern.Safe)
    Ok(Json.toJson(movie))
    /*val movies = Movie.findAll
    Ok(html.list(movies))*/
  } */

  def addMovie = Action {
    implicit request =>
      def values = moviesForm.bindFromRequest()(request).data
      def form_title = values("title")
      def form_genre = values("genre")
      def form_description = values("description")
      def form_director = values("director")
      def form_writer = values("writer")
      def form_actors = values("actors")
      Movie.save(Movie(
        title = form_title,
        genre = form_genre,
        description = form_description,
        director = form_director,
        writer =  form_writer,
        actors = form_actors
      ))
      //Movie.saveData("some title", "some description...")
      val movies = Movie.findAll
      Ok(html.list(movies))
  }

  def removeMovie(id: ObjectId) = Action {
    Movie.removeById(id)
    val movies = Movie.findAll
    Ok(html.list(movies))
  }
}