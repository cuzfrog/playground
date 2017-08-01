package com.github.cuzfrog


object AdHocOverloading extends App {

  implicit class UnboxOps[T, R, B](b: B)(implicit ev: UnboxEv[T, B, R], ev1: B <:< Box[T]) {
    def value: R = ev.unbox(b)
  }

  val optional = Box(Some(3))
  val confident = new Box(Some("C")) with Confidence
  val otherType = Seq("bad")

  optional.value
  confident.value

  //otherType.value //compile time error

  println(optional.value)
  //Some(3)
  println(confident.value)
  //C
}

trait UnboxEv[+T, -B, +R] {
  def unbox(b: B): R
}

trait Confidence
case class Box[+T](v: Option[T])
trait LowLevelImplicitOfBox {
  this: Box.type =>
  implicit def optionEvidence[T]: UnboxEv[T, Box[T], Option[T]] =
    new UnboxEv[T, Box[T], Option[T]] {
      override def unbox(b: Box[T]): Option[T] = b.v
    }
}
object Box extends LowLevelImplicitOfBox {
  implicit def confidentEvidence[T]: UnboxEv[T, Box[T] with Confidence, T] =
    new UnboxEv[T, Box[T] with Confidence, T] {
      override def unbox(b: Box[T] with Confidence): T = b.v.get
    }
}