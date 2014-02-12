package as.chess.problem.geom.transform.array

class ClockwiseTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val ct = new as.chess.problem.geom.transform.point.ClockwiseTransformer(boardWidth, boardHeight)

  def apply[T](input: Array[Array[T]], output: Array[Array[T]], numberOf90rotations: Int) {
    for (y ← 0 until boardHeight; x ← 0 until boardWidth) {
      val p = ct.apply(x, y, numberOf90rotations)
      output(p._2)(p._1) = input(y)(x)
    }
  }

  def rotate90[T](input: Array[Array[T]], output: Array[Array[T]]) = apply(input, output, 1)

  def rotate180[T](input: Array[Array[T]], output: Array[Array[T]]) = apply(input, output, 2)

  def rotate270[T](input: Array[Array[T]], output: Array[Array[T]]) = apply(input, output, 3)
}
