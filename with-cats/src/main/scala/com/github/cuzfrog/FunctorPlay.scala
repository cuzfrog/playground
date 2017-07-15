package com.github.cuzfrog

import cats.data.Nested
import cats.syntax.traverse._
import cats.instances.list._

object FunctorPlay extends App{
  val list = List(Option(List(1, 5)), None, Option(Seq(2, 6, 7)))

  val nested = Nested(list)


  println(list.traverse(opt => List(opt.map(_.size))))


}
