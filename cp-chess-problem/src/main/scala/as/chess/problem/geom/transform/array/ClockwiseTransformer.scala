package as.chess.problem.geom.transform.array

import as.chess.problem.geom.transform._

class ClockwiseTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val ct = new as.chess.problem.geom.transform.point.ClockwiseTransformer(boardWidth, boardHeight)

  def apply[T](input: Array[Array[T]], output: Array[Array[T]], clockwiseQuadrantRotation: ClockwiseQuadrantRotation) {
    for (y ← 0 until boardHeight; x ← 0 until boardWidth) {
      val p = ct.apply(x, y, clockwiseQuadrantRotation)
      output(p._2)(p._1) = input(y)(x)
    }
  }

  def rotate90[T](input: Array[Array[T]], output: Array[Array[T]]) = apply(input, output, ClockwiseQuadrantRotation90)

  def rotate180[T](input: Array[Array[T]], output: Array[Array[T]]) = apply(input, output, ClockwiseQuadrantRotation180)

  def rotate270[T](input: Array[Array[T]], output: Array[Array[T]]) = apply(input, output, ClockwiseQuadrantRotation270)
}
