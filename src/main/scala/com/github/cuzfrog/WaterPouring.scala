package com.github.cuzfrog

object WaterPouring extends App {
  private val problem = new Pouring(Vector(4, 6))
  println(problem.solutions(5))
}

private class Pouring(capacity: Vector[Int]) {
  type State = Vector[Int]
  private val initialState: State = capacity map (_ => 0)

  trait Move extends Product with Serializable {
    def change(state: State): State
  }
  private case class Empty(glass: Int) extends Move {
    override def change(state: State): State = state.updated(glass, 0)
  }
  private case class Fill(glass: Int) extends Move {
    override def change(state: State): State = state.updated(glass, capacity(glass))
  }
  private case class Pour(from: Int, to: Int) extends Move {
    override def change(state: State): State = {
      val amount = state(from) min (capacity(to) - state(to))
      state updated(from, state(from) - amount) updated(to, state(to) + amount)
    }
  }

  private val glasses = capacity.indices.toSet

  private val moves =
    (for (glass <- glasses) yield Empty(glass)) ++
      (for (glass <- glasses) yield Fill(glass)) ++
      (for (from <- glasses; to <- glasses; if from != to) yield Pour(from, to))

  case class Path(history: Seq[Move], endState: State) {
    def extend(move: Move): Path = Path(move +: history, move.change(endState))
    override def toString: String = (history.reverse mkString " ") + "-->" + endState
  }

  private val initialPath = Path(Nil, initialState)

  private def pathSets(paths: Set[Path], visited: Set[State]): Stream[Set[Path]] =
    if (paths.isEmpty) Stream.empty
    else {
      lazy val nextSet = for {
        path <- paths
        next <- moves map path.extend
        if !visited.contains(next.endState)
      } yield next
      paths #:: pathSets(nextSet, nextSet.map(_.endState) ++ visited)
    }

  def solutions(target: Int): Stream[Path] =
    if (capacity.isEmpty) Stream.empty
    else {
      for {
        paths <- pathSets(Set(initialPath), Set.empty)
        path <- paths
        if path.endState contains target
      } yield path
    }
}