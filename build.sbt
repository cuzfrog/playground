import sbt.Keys._
import Settings._

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
onLoad in Global := (onLoad in Global).value andThen (Command.process(s"", _))

inThisBuild(Seq(
  scalaOrganization := "org.typelevel",
  scalaVersion := "2.12.2-bin-typelevel-4"
))

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "scala-playground",
    version := "0.0.1",
    libraryDependencies ++= Seq(
      "org.openjdk.jol" % "jol-core" % "0.8"
    ),
    javaAgents += "com.github.jbellis" % "jamm" % "0.3.1" % "compile;runtime"
  ).enablePlugins(JavaAgent)

lazy val withLib = (project in file("./with-lib"))
  .settings(commonSettings)
  .settings(
    name := "scala-playground-with-libs",
    version := "0.0.1",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0"
    )
  )