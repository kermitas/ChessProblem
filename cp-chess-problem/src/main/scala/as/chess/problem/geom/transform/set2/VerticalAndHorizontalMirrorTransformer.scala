package as.chess.problem.geom.transform.set2

import as.chess.problem.piece.PositionedPiece
import scala.collection.generic.CanBuildFrom

class VerticalAndHorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val vhmt = new as.chess.problem.geom.transform.point.VerticalAndHorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(set: Set[PositionedPiece])(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]): Set[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(vhmt(pp.x, pp.y), pp.piece))(setBuilder)
}
