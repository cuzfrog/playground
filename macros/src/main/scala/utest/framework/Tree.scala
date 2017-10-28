package utest.framework

/**
 * An immutable tree with each node containing a value, and a `Seq` of
 * children. Provides all normal `Seq` functionality as well as some tree
 * specific methods.
 */
case class Tree[+T](value: T, children: Tree[T]*){
  /**
   * The number of nodes in this tree.
   */
  def length: Int = {
    children.foldLeft(1)(_ + _.length)
  }

  def map[V](f: T => V): Tree[V] = {
    Tree(f(value), children.map(_.map(f)):_*)
  }
  /**
   * An iterator over the values stored on the nodes of this tree, in a depth
   * first manner starting from the root.
   */
  def iterator: Iterator[T] = {
    Iterator(this.value) ++ children.flatMap(_.iterator)
  }

  def leafPaths: Iterator[List[T]] = {
    if (children.isEmpty) Iterator(List(this.value))
    else children.toIterator.flatMap(_.leafPaths).map(this.value :: _)
  }

  def toSeq: Seq[T] = iterator.toList
  /**
   * Returns an iterator for the values at the leaves of this tree
   */
  def leaves: Iterator[T] = {
    if (children.isEmpty) Iterator(this.value)
    else children.toIterator.flatMap(_.leaves)
  }

}


/**
 * The executable portion of a tree of tests. Each node contains an
 * executable, which when run either returns a Left(result) or a
 * Right(sequence) of child nodes which you can execute.
 */
class TestCallTree(inner: => Either[Any, IndexedSeq[TestCallTree]]){
  /**
   * Runs the test in this [[TestCallTree]] at the specified `path`. Called
   * by the [[TestTreeSeq.run]] method and usually not called manually.
   */
  def run(path: List[Int]): Any = {
    path match {
      case head :: tail =>
        val Right(children) = inner
        children(head).run(tail)
      case Nil =>
        val Left(res) = inner
        res
    }
  }
}