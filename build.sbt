import sbt.Keys._
import Settings._

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
onLoad in Global := (onLoad in Global).value andThen (Command.process(s"", _))

inThisBuild(Seq(
  //scalaOrganization := "org.typelevel",
  //scalaVersion := "2.12.2-bin-typelevel-4",
  scalaVersion := "2.12.3",
  version := "0.0.1"
))

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "scala-playground",
    libraryDependencies ++= Seq(
      "org.openjdk.jol" % "jol-core" % "0.8"
    ),
    libraryDependencies ++= Seq(
      "com.twitter" %% "scrooge-core" % "4.16.0"
    ),
    javaAgents += "com.github.jbellis" % "jamm" % "0.3.1" % "compile;runtime"
  ).enablePlugins(JavaAgent)

lazy val withCats = (project in file("./with-cats"))
  .settings(commonSettings)
  .settings(
    name := "scala-playground-with-cats",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0"
    )
  )

val scalazVersion = "7.2.13"
lazy val withScalaz = (project in file("./with-scalaz"))
  .settings(commonSettings)
  .settings(
    name := "scala-playground-with-scalaz",
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % scalazVersion,
      "org.scalaz" %% "scalaz-effect" % scalazVersion,
      //"org.scalaz" %% "scalaz-typelevel" % scalazVersion,
      "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test"
    ),
    libraryDependencies ++= Dependencies.monocle,
    reColors := Seq("magenta"),
    initialCommands in console := "import scalaz._, Scalaz._"
  )