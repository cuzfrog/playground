import sbt.Keys._
import Settings._

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
onLoad in Global := (onLoad in Global).value andThen (Command.process(s"", _))


lazy val root = (project in file("."))
  .settings(commonSettings, publicationSettings, readmeVersionSettings)
  .settings(
    name := "sbt-template",
    version := "0.0.1",
    libraryDependencies ++= Seq(

    ),
    reColors := Seq("magenta")
  )