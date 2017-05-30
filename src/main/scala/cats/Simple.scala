package cats


object Simple {


  def combineAll[A: Monoid](list: List[A]): A =
    list.foldRight(implicitly[Monoid[A]].empty)(implicitly[Monoid[A]].combine)

  println(combineAll(List(Pair(1, "hello"), Pair(2, " "), Pair(3, "world"))))
}


trait Monoid[A] {
  def empty: A
  def combine(x: A, y: A): A
}

object Monoid {
  implicit val intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0
    def combine(x: Int, y: Int): Int = x + y
  }

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    def empty: String = ""
    def combine(x: String, y: String): String = x ++ y
  }
}

final case class Pair[A, B](first: A, second: B)

object Pair {
  implicit def tuple2Instance[A: Monoid, B: Monoid]: Monoid[Pair[A, B]] =
    new Monoid[Pair[A, B]] {
      def empty: Pair[A, B] = Pair(implicitly[Monoid[A]].empty, implicitly[Monoid[B]].empty)

      def combine(x: Pair[A, B], y: Pair[A, B]): Pair[A, B] =
        Pair(implicitly[Monoid[A]].combine(x.first, y.first), implicitly[Monoid[B]].combine(x.second, y.second))
    }
}