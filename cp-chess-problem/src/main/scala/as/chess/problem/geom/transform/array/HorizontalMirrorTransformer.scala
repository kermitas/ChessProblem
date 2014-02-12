package as.chess.problem.geom.transform.array

class HorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  def apply[T](input: Array[Array[T]], output: Array[Array[T]]) {
    for (y ← 0 until boardHeight; x ← 0 until boardWidth) output(y)(x) = input(boardHeight - 1 - y)(x)
  }
}
