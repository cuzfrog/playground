package anywhere

import scala.util.Try
import org.scalacheck.Prop.forAll

object Tmp extends App {

  val leftUnit = forAll { (v1: Int, v2: Int) =>
    val l = Try(v1).flatMap { v => Try(v1 / v2) }
    val r = Try(v1 / v2)
    println(l)
    println(r)
    l == r
  }
  leftUnit.check

  val rightUnit = forAll { (v1: Int, v2: Int) =>
    Try(v1 / v2).flatMap {Try(_)} == Try(v1 / v2)
  }
  rightUnit.check

  Stream.empty.take(1)
}
