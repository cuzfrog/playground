package anywhere

import scala.util.Try
import org.scalacheck.Prop.forAll

object Tmp {
  def main(ags: Array[String]): Unit = {
    val test = new ClosureTest

    test.closure1()
    test.closure2()

    println(test.marker)
  }
}

class ClosureTest {
  var marker: Int = 0
  val closure1 = () => marker += 1
  val closure2 = () => marker += 1
}