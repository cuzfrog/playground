import sbt.Keys._
import sbt._
import MyTasks._

object Settings {
  val commonSettings = Seq(
    resolvers ++= Seq(
      Resolver.bintrayRepo("cuzfrog","maven")
    ),
    organization := "com.github.cuzfrog",
    scalacOptions ++= Seq(
      "-Xlint",
      "-unchecked",
      "-deprecation",
      "-feature"),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "utest" % "0.5.4" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
      "org.mockito" % "mockito-core" % "2.11.0" % "test"
    ),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    logBuffered in Test := false,
    parallelExecution in Test := false,
    licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0"))
  )

  val publicationSettings = Seq(
    publishTo := Some("My Bintray" at s"https://api.bintray.com/maven/cuzfrog/maven/${name.value }/;publish=1")
  )

  val readmeVersionSettings = Seq(
    (compile in Compile) := ((compile in Compile) dependsOn versionReadme).value,
    versionReadme := {
      val contents = IO.read(file("README.md"))
      val regex =raw"""(?<=libraryDependencies \+= "com\.github\.cuzfrog" %% "${name.value}" % ")[\d\w\-\.]+(?=")"""
      val newContents = contents.replaceAll(regex, version.value)
      IO.write(file("README.md"), newContents)
    }
  )
}