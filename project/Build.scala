import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play-project"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "se.radley" %% "play-plugins-salat" % "1.3.0"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    routesImport += "se.radley.plugin.salat.Binders._",
    templatesImport += "org.bson.types.ObjectId",
    resolvers += Resolver.sonatypeRepo("snapshots")
  )

}
