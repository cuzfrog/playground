package com.github.cuzfrog

object CaseMatchMixin extends App {

  trait T
  sealed trait A[S]
  class A1[S] extends A[S]
  class A2[S] extends A[S]

  def get[N <: A[S], S](a: N): N = {
    a match {
      case a1: A1[S] => println("a1")
      case a2: A2[S] => println("a2")
    }
    a
  }


}
