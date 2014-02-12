package as.chess.problem.board.path.geom.transform

import as.chess.problem.geom.PositionedPiece

class ClockwiseTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val ct = new as.chess.problem.geom.transform.point.ClockwiseTransformer(boardWidth, boardHeight)

  def apply(path: List[PositionedPiece], numberOf90rotations: Int): List[PositionedPiece] = path.map(pp ⇒ new PositionedPiece(ct(pp.x, pp.y, numberOf90rotations), pp.piece))

  def rotate90(path: List[PositionedPiece]) = apply(path, 0)

  def rotate180(path: List[PositionedPiece]) = apply(path, 1)

  def rotate270(path: List[PositionedPiece]) = apply(path, 2)
}
