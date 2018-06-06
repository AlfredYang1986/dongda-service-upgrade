import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayScala
import scala.language.experimental.macros

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
addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
	guice,

    "org.mongodb" % "casbah_2.11" % "3.1.1",
	"io.circe" %% "circe-core" % "0.9.3",
	"io.circe" %% "circe-generic" % "0.9.3",
	"io.circe" %% "circe-parser" % "0.9.3",
	"com.dripower" % "play-circe_2.11" % "2609.1",
	"io.spray" % "spray-httpx_2.11" % "1.3.3",
	"org.mongodb" % "casbah_2.11" % "3.1.1",

    // "com.pharbers" % "pharbers-module" % "0.1",
    // "com.pharbers" % "pharbers-errorcode" % "0.1",
    // "com.pharbers" % "pharbers-mongodb" % "0.1",
    // "com.pharbers" % "pharbers-third" % "0.1",
    // "com.pharbers" % "pharbers-spark" % "0.1",
    // "com.pharbers" % "pharbers-memory" % "0.1",
    // "com.pharbers" % "pharbers-security" % "0.1",
    // "com.pharbers" % "pharbers-message" % "0.1",
    // "com.pharbers" % "pharbers-redis" % "0.1",
    // "com.pharbers" % "pharbers-max" % "0.1",
    // "com.pharbers" % "pharbers-pattern" % "0.1",
    // "com.pharbers" % "pharbers-errorcode" % "0.1",
    // "com.pharbers" % "pharbers-redis" % "0.1",

	"log4j" % "log4j" % "1.2.17",
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
    "org.specs2" % "specs2_2.11" % "3.7" % Test
)

