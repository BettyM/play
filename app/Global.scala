/**
 * Created with IntelliJ IDEA.
 * User: beatriz.murillo
 * Date: 9/6/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */

import com.mongodb.casbah.Imports._
import play.api._
import libs.ws.WS
import models._
import se.radley.plugin.salat._

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    Logger.info("onStart...")
      if( Movie.count(DBObject(), Nil, Nil) == 0 ) {
          Logger.info("Loading data...")
          Movie.save(Movie(
            title = "A Clockwork Orange",
            genre = "Crime, Drama, Sci-Fi",
            description = "In future Britain, charismatic delinquent Alex DeLarge is jailed and volunteers for an experimental aversion therapy developed by the government in an effort to solve society's crime problem... but not all goes to plan.",
            director = "Stanley Kubrick",
            writer =  "Anthony Burgess",
            actors = "Malcolm McDowell, Patrick Magee, Michael Bates "
          ))

          Movie.save(Movie(
            title = "Oldboy",
            genre = "Drama, Mystery, Thriller",
            description = "After being kidnapped and imprisoned for 15 years, Oh Dae-Su is released, only to find that he must find his captor in 5 days.",
            director = "Chan-wook Park",
            writer = "Garon Tsuchiya",
            actors = "Min-sik Choi, Ji-tae Yu, Hye-jeong Kang"
          ))
      }

  }

}
