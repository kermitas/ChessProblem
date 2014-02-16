package as.chess.problem.geom.transform.set2

import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform._
import scala.collection.generic.CanBuildFrom

class ClockwiseTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val ct = new as.chess.problem.geom.transform.point.ClockwiseTransformer(boardWidth, boardHeight)

  def apply(set: Set[PositionedPiece], clockwiseQuadrantRotation: ClockwiseQuadrantRotation)(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]): Set[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(ct(pp.x, pp.y, clockwiseQuadrantRotation), pp.piece))(setBuilder)

  def rotate90(set: Set[PositionedPiece])(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]) = apply(set, ClockwiseQuadrantRotation90)(setBuilder)

  def rotate180(set: Set[PositionedPiece])(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]) = apply(set, ClockwiseQuadrantRotation180)(setBuilder)

  def rotate270(set: Set[PositionedPiece])(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]) = apply(set, ClockwiseQuadrantRotation270)(setBuilder)
}
