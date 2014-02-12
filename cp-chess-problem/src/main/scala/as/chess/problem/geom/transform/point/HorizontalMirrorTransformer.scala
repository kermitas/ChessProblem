package as.chess.problem.geom.transform.point

class HorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {
  def apply(x: Int, y: Int): (Int, Int) = (x, boardHeight - 1 - y)
  def apply(p: (Int, Int)): (Int, Int) = apply(p._1, p._2)
}