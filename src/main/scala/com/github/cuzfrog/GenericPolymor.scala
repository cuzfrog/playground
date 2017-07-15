package com.github.cuzfrog

import java.awt.Color

object GenericPolymor {


  trait Pet[A] {
    def name(a: A): String
    def renamed(a: A, newName: String): A
  }

  implicit class PetOps[A](a: A)(implicit ev: Pet[A]) {
    def name = ev.name(a)
    def renamed(newName: String): A = ev.renamed(a, newName)
  }

  case class Fish(name: String, age: Int)

  object Fish {
    implicit object FishPet extends Pet[Fish] {
      def name(a: Fish) = a.name
      def renamed(a: Fish, newName: String) = a.copy(name = newName)
    }
  }

  case class Kitty(name: String, color: Color)

  object Kitty {
    implicit object KittyPet extends Pet[Kitty] {
      def name(a: Kitty) = a.name
      def renamed(a: Kitty, newName: String) = a.copy(name = newName)
    }
  }

  def esquire[A: Pet](a: A): A = a.renamed(a.name + ", Esq.")

  val bob = Fish("Bob", 12)
  val thor = Kitty("Thor", Color.ORANGE)


  val l1 = List[(A, Pet[A]) forSome {type A}]((bob, implicitly[Pet[Fish]]), (thor, implicitly[Pet[Kitty]]))
  l1.map { case (a, ev) => esquire(a)(ev) }.foreach(println)
}
