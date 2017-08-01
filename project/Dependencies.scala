import sbt.Keys._
import sbt._

object Dependencies {
  val monocle = {
    val monocleVersion = "1.4.0"
    Seq(
      "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
      "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
      "com.github.julien-truffaut" %% "monocle-law" % monocleVersion % "test"
    )
  }
}