import sbt.Keys._
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val commonSettings = Seq(
  resolvers ++= Seq(
    Resolver.mavenLocal,
    "bintray-cuzfrog-maven" at "http://dl.bintray.com/cuzfrog/maven",
    "Artima Maven Repository" at "http://repo.artima.com/releases",
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    "spray repo" at "http://repo.spray.io"
  ),
  organization := "com.github.cuzfrog",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-unchecked", "-feature"),
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.7" % "test",
    "junit" % "junit" % "4.12" % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test->default",
    "org.scalacheck" %% "scalacheck" % "1.13.2" % "test",
    "com.icegreen" % "greenmail" % "1.5.1" % "test",
    "org.mockito" % "mockito-core" % "1.10.19" % "test"
  ),
  logBuffered in Test := false,
  testOptions += Tests.Argument(TestFrameworks.JUnit, "-v", "-q", "-a"),
  parallelExecution in Test := false
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "sbt-template",
    version := "0.0.1",
    libraryDependencies ++= Seq(

    ),
    publishTo := Some("My Bintray" at "https://api.bintray.com/maven/cuzfrog/maven/--PROJECT NAME--/;publish=1"),
    credentials += Credentials("Bintray API Realm", "api.bintray.com", "BINTRAY_USER", "BINTRAY_PASS"),
    compile in Compile <<= (compile in Compile) dependsOn versionReadme,
    versionReadme := {
      val contents = IO.read(file("README.md"))
      val regex ="""(?<=libraryDependencies \+= "com\.github\.cuzfrog" %% "maila" % ")[\d\w\-\.]+(?=")"""
      val newContents = contents.replaceAll(regex, version.value)
      IO.write(file("README.md"), newContents)
    },
    reColors := Seq("magenta")
  )

lazy val generateBat = TaskKey[Unit]("generate-bat", "Generate a bat file for window shell.")
lazy val copyApp = TaskKey[Unit]("copy-app", "Copy app files to target.")
lazy val cleanAll = TaskKey[Unit]("clean-all", "Clean all files in target folders.")
lazy val versionReadme = TaskKey[Unit]("version-readme", "Update version in README.MD")

