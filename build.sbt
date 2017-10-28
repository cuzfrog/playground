import sbt.Keys._
import Settings._

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
onLoad in Global := {
  val insertCommand: State => State =
    (state: State) =>
      state.copy(remainingCommands = Exec("", None) +: state.remainingCommands)
  (onLoad in Global).value andThen insertCommand
}

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
    initialCommands in console := "import scalaz._, Scalaz._"
  )

val macroAnnotationSettings = Seq(
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
  scalacOptions in(Compile, console) ~= (_ filterNot (_ contains "paradise")),
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % Provided
)
val libraryTest = project
  .settings(macroAnnotationSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.github.cuzfrog" %% "scmd" % "0.1.0"
    )
  )

lazy val macros = project
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  )

lazy val `macro-test` = project.dependsOn(macros)
  .settings(

  )