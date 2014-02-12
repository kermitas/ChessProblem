package as.chess.problem.geom.transform.point

class ClockwiseTransformer(boardWidth: Int, boardHeight: Int) extends Serializable {

  val centerX: Double = {
    val doubleBoardWidth: Double = boardWidth
    doubleBoardWidth / 2
  }
  val centerY: Double = {
    val doubleBoardHeight: Double = boardHeight
    (doubleBoardHeight / 2) * -1
  }

  val ats = {
    val at0 = java.awt.geom.AffineTransform.getQuadrantRotateInstance(0, centerX, centerY)
    val at90 = java.awt.geom.AffineTransform.getQuadrantRotateInstance(-1, centerX, centerY)
    val at180 = java.awt.geom.AffineTransform.getQuadrantRotateInstance(-2, centerX, centerY)
    val at270 = java.awt.geom.AffineTransform.getQuadrantRotateInstance(-3, centerX, centerY)

    Array(at90, at90, at180, at270)
  }

  def apply(x: Int, y: Int, numberOf90rotations: Int): (Int, Int) = {
    val array = Array[Double](x + 0.5, (y + 0.5) * -1)
    ats(numberOf90rotations).transform(array, 0, array, 0, 1)
    (Math.floor(array(0) - 0.5).toInt, Math.floor((array(1) * -1) - 0.5).toInt)
  }

  def rotate90(x: Int, y: Int): (Int, Int) = apply(x, y, 1)
  def rotate90(p: (Int, Int)): (Int, Int) = rotate90(p._1, p._2)

  def rotate180(x: Int, y: Int): (Int, Int) = apply(x, y, 2)
  def rotate180(p: (Int, Int)): (Int, Int) = rotate180(p._1, p._2)

  def rotate270(x: Int, y: Int): (Int, Int) = apply(x, y, 3)
  def rotate270(p: (Int, Int)): (Int, Int) = rotate270(p._1, p._2)
}