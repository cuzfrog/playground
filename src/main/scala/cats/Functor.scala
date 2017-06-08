package cats


private trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

private object Functor {

  def fmap[F[_] : Functor, A, B](fa: F[A])(f: A => B): F[B] =
    implicitly[Functor[F]].map(fa)(f)

  implicit val functorForList: Functor[List] = new Functor[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa.map(f)
  }
}

private case class Box2[A](l: A, r: A)

private[cats] object FunctorRun extends App {
  val l1 = List(1, 2, 3, 4, 7, 8)
  val l2 = List("a", "c", "d", "e")

  println(Functor.fmap(l1)(_ + 1))

  val b2 = Box2(1, 2)

}