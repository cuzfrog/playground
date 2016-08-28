shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val commonSettings = Seq(
  organization := "com.github.cuzfrog",
  scalaVersion := "2.11.8",
  logBuffered in Test := false,
  scalacOptions ++= Seq("-unchecked", "-feature"),
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.7" % "test",
    "junit" % "junit" % "4.12" % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test->default",
    "org.scalacheck" %% "scalacheck" % "1.13.2" % "test",
    "com.icegreen" % "greenmail" % "1.5.1" % "test",
    "org.mockito" % "mockito-core" % "1.10.19" % "test"
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "sbt-template",
    version := "0.0.1",
    libraryDependencies ++= Seq(

    ),
    publishTo := Some("My Bintray" at "https://api.bintray.com/maven/cuzfrog/maven/--PROJECT NAME--/;publish=1"),
    credentials += Credentials("Bintray API Realm", "api.bintray.com", "BINTRAY_USER", "BINTRAY_PASS")
  )


