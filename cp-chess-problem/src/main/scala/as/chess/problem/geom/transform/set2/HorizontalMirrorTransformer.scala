package as.chess.problem.geom.transform.set2

import as.chess.problem.piece.PositionedPiece
import scala.collection.generic.CanBuildFrom

class HorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val hmt = new as.chess.problem.geom.transform.point.HorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(set: Set[PositionedPiece])(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]): Set[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(hmt(pp.x, pp.y), pp.piece))(setBuilder)
}
