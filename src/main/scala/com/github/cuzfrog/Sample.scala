package com.github.cuzfrog

object Sample {
  val x: Foo[Seq]#t[Int] = List(1)

  val m1 = Map.empty
}

trait Foo[M[_]] { type t[A] = M[A] }
