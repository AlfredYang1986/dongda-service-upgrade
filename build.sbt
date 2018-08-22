import play.sbt.PlayScala
import scala.language.experimental.macros
import play.routes.compiler.InjectedRoutesGenerator

val paradiseVersion = "2.1.0"
def common = Seq(
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.11.8", "2.12.6"),
    version := "2.0",
    organization := "com.blackmirror"
)

lazy val root = (project in file(".")).
        enablePlugins(PlayScala).
        settings(common: _*).
        settings(
            name := "dongda-service",
            fork in run := true,
            javaOptions += "-Xmx2G"
        )

routesGenerator := InjectedRoutesGenerator

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")
addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
    guice,

    "org.scala-lang" % "scala-reflect" % "2.11.8",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
    "org.typelevel" %% "macro-compat" % "1.1.1",
    "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided",

    "org.mongodb" % "casbah_2.11" % "3.1.1",
    "io.circe" %% "circe-core" % "0.9.3",
    "io.circe" %% "circe-generic" % "0.9.3",
    "io.circe" %% "circe-parser" % "0.9.3",
    "com.dripower" % "play-circe_2.11" % "2609.1",
    "io.spray" % "spray-httpx_2.11" % "1.3.3",

	"com.pharbers" % "jsonapi" % "1.0",
	"com.pharbers" % "macros" % "1.0",
	"com.pharbers" % "pattern" % "1.0",

    "log4j" % "log4j" % "1.2.17",
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
    "org.specs2" % "specs2_2.11" % "3.7" % Test
)