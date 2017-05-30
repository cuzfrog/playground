package com.github.cuzfrog

import java.awt.Color

object FBound {

  trait Pet[A <: Pet[A]] {
    this: A =>
    def name: String
    def renamed(newName: String): A
  }

  case class Fish(name: String, age: Int) extends Pet[Fish] {
    def renamed(newName: String) = copy(name = newName)
  }

  case class Kitty(name: String, color: Color) extends Pet[Kitty] {
    def renamed(newName: String) = copy(name = newName)
  }

  def esquire[A <: Pet[A]](a: A): A = a.renamed(a.name + ", Esq.")

  val bob = Fish("Bob", 12)
  val thor = Kitty("Thor", Color.ORANGE)

  val l1 = List[A forSome { type A <: Pet[A] }](bob, thor)

  l1.map(e => esquire(e)).foreach(println)
}


