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
        lastUpper = false
        Seq(char)
      }
    }
  }

  println(camelCase2hyphen(args.head))
}
