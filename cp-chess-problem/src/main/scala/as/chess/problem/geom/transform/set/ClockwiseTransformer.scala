package as.chess.problem.geom.transform.set

import scala.collection.{ Set ⇒ GenericSet }
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform._

class ClockwiseTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val ct = new as.chess.problem.geom.transform.point.ClockwiseTransformer(boardWidth, boardHeight)

  def apply(set: GenericSet[PositionedPiece], clockwiseQuadrantRotation: ClockwiseQuadrantRotation): GenericSet[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(ct(pp.x, pp.y, clockwiseQuadrantRotation), pp.piece))

  def rotate90(set: GenericSet[PositionedPiece]) = apply(set, ClockwiseQuadrantRotation90)

  def rotate180(set: GenericSet[PositionedPiece]) = apply(set, ClockwiseQuadrantRotation180)

  def rotate270(set: GenericSet[PositionedPiece]) = apply(set, ClockwiseQuadrantRotation270)
}
