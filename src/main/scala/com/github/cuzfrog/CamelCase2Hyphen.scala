package com.github.cuzfrog

object CamelCase2Hyphen extends App {

  @inline
  protected def camelCase2hyphen(camelCase: String): String = {
    var lastUpper: Boolean = true //ignore first char
    camelCase.flatMap { char =>
      if (char.isUpper && !lastUpper) {
        lastUpper = true
        Seq('-', char.toLower)
      }
      else {
        if (char.isLower) lastUpper = false
        Seq(char.toLower)
      }
    }
  }

  println(camelCase2hyphen(args.head))
}
