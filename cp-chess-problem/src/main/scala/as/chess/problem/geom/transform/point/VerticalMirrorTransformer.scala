package as.chess.problem.geom.transform.point

class VerticalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {
  def apply(x: Int, y: Int): (Int, Int) = (boardWidth - 1 - x, y)
  def apply(p: (Int, Int)): (Int, Int) = apply(p._1, p._2)
}