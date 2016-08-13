shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

resolvers ++= Seq(
  "Local Maven Repository" at """file:///""" + Path.userHome.absolutePath +"""\.m2\repository""",
  "bintray-cuzfrog-maven" at "http://dl.bintray.com/cuzfrog/maven"
)


lazy val root = (project in file("."))
  .settings(
    organization := "com.github.cuzfrog",
    name := "sbt-template",
    version := Settings.version,
    scalaVersion := Settings.scalaVersion,
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.12" % "test",
      "com.novocode" % "junit-interface" % "0.11" % "test->default",
      "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
    ),
    publishTo := Some("My Bintray" at "https://api.bintray.com/maven/cuzfrog/maven/--PROJECT NAME--/;publish=1"),
    credentials += Credentials("Bintray API Realm", "api.bintray.com", "BINTRAY_USER", "BINTRAY_PASS")
  )


