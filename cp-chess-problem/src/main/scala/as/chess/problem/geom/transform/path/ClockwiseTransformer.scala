package as.chess.problem.geom.transform.path

import as.chess.problem.piece.PositionedPiece

class ClockwiseTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val ct = new as.chess.problem.geom.transform.point.ClockwiseTransformer(boardWidth, boardHeight)

  def apply(path: List[PositionedPiece], numberOf90rotations: Int): List[PositionedPiece] = path.map(pp ⇒ new PositionedPiece(ct(pp.x, pp.y, numberOf90rotations), pp.piece))

  def rotate90(path: List[PositionedPiece]) = apply(path, 1)

  def rotate180(path: List[PositionedPiece]) = apply(path, 2)

  def rotate270(path: List[PositionedPiece]) = apply(path, 3)
}
