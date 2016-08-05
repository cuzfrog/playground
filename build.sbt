organization := "com.github.cuzfrog"
name := "sbt-template"
version := Settings.version
scalaVersion := Settings.scalaVersion

lazy val root = (project in file("."))

resolvers ++= Seq(
  "Local Maven Repository" at """file:///"""+Path.userHome.absolutePath+"""\.m2\repository""",
  "bintray-cuzfrog-maven" at "http://dl.bintray.com/cuzfrog/maven"
)

libraryDependencies ++= Seq(
  
)

publishTo := Some("My Bintray" at "https://api.bintray.com/maven/cuzfrog/maven/--PROJECT NAME--/;publish=1")
credentials += Credentials("Bintray API Realm", "api.bintray.com", "BINTRAY_USER", "BINTRAY_PASS")