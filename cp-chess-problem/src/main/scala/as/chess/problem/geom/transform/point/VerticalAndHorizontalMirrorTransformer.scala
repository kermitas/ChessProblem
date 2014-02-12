package as.chess.problem.geom.transform.point

class VerticalAndHorizontalMirrorTransformer(boardWidth: Int, boardHeight: Int) extends Serializable {

  protected def vmt = new VerticalMirrorTransformer(boardWidth, boardHeight)
  protected def hmt = new HorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(x: Int, y: Int): (Int, Int) = hmt(vmt(x, y))
  def apply(p: (Int, Int)): (Int, Int) = apply(p._1, p._2)
}