organization := "com.github.cuzfrog"
name := "xx"
version := "0.0.1-p1"
scalaVersion := "2.11.8"

lazy val root = (project in file("."))

resolvers ++= Seq(
  "Local Maven Repository" at """file:///"""+Path.userHome.absolutePath+"""\.m2\repository""",
  "bintray-cuzfrog-maven" at "http://dl.bintray.com/cuzfrog/maven"
)

libraryDependencies ++= Seq(
  
)


EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.ManagedClasses
EclipseKeys.withSource := true
EclipseKeys.withJavadoc := true
