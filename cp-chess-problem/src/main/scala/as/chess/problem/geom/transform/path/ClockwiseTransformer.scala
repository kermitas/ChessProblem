package as.chess.problem.geom.transform.path

import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform._

class ClockwiseTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val ct = new as.chess.problem.geom.transform.point.ClockwiseTransformer(boardWidth, boardHeight)

  def apply(path: List[PositionedPiece], clockwiseQuadrantRotation: ClockwiseQuadrantRotation): List[PositionedPiece] = path.map(pp ⇒ new PositionedPiece(ct(pp.x, pp.y, clockwiseQuadrantRotation), pp.piece))

  def rotate90(path: List[PositionedPiece]) = apply(path, ClockwiseQuadrantRotation90)

  def rotate180(path: List[PositionedPiece]) = apply(path, ClockwiseQuadrantRotation180)

  def rotate270(path: List[PositionedPiece]) = apply(path, ClockwiseQuadrantRotation270)
}
