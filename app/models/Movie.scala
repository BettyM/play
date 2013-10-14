package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
//import com.novus.salat.global._
import com.novus.salat.annotations._
import com.novus.salat.dao._
//import com.novus.salat.dao.{ SalatDAO, ModelCompanion }
import com.mongodb.casbah.Imports._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._
import mongoContext._

//import play.api.mvc.{JavascriptLitteral, QueryStringBindable}


case class Movie (
              @Key("_id")id: ObjectId = new ObjectId,
              title: String,
              genre: String,
              description: String,
              release_date: Option[Date] = None,
              director: String,
              writer: String,
              actors: String
            )

object Movie extends MovieDAO with MovieJson

trait MovieDAO extends ModelCompanion[Movie, ObjectId] {
  def collection = mongoCollection("movies")
  val dao = new SalatDAO[Movie, ObjectId](collection) {}

  //Queries
  def findByTitle(title: String): Option[Movie] = dao.findOne(MongoDBObject("title" -> title))
  def updateById(movie: Movie) = dao.update(MongoDBObject("_id" -> movie.id),
    MongoDBObject("$set" -> MongoDBObject("title" -> movie.title, "genre" -> movie.genre, "description" -> movie.description, "release_date" -> movie.release_date, "director" -> movie.director, "writer" -> movie.writer, "actors" -> movie.actors)),
    false, false, new WriteConcern)
  //def removeById(id: ObjectId): Option[Movie] = dao.removeById()
  //def findOne[A <% DBObject](t: A, rp: ReadPreference = defaultReadPreference) = dao.findOne(t, rp)
  //def find[A <% DBObject, B <% DBObject](ref: A, keys: B, rp: ReadPreference = defaultReadPreference) = dao.find(ref, keys, rp)
  /*def saveData(title: String, desc: String) = {
    //val data = new Movie(title, genre, desc, date, director, writer, actors)
    dao.insert(data)
  } */
}


/**
 * Trait used to convert to and from json
 */
trait MovieJson {

  implicit val movieJsonWrite = new Writes[Movie] {
    def writes(u: Movie): JsValue = {
      Json.obj(
        "id" -> u.id,
        "title" -> u.title,
        "genre" -> u.genre,
        "description" -> u.description,
        "release_date" -> u.release_date,
        "director" -> u.director,
        "writer" -> u.writer,
        "actors" -> u.actors
      )
    }
  }
  implicit val movieJsonRead = (
    (__ \ 'id).read[ObjectId] ~
      (__ \ 'title).read[String] ~
      (__ \ 'genre).read[String] ~
      (__ \ 'description).read[String] ~
      (__ \ 'release_date).readNullable[Date] ~
      (__ \ 'director).read[String] ~
      (__ \ 'writer).read[String] ~
      (__ \ 'actors).read[String]
    )(Movie.apply _)
}