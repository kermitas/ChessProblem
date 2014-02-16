package as.chess.problem.geom.transform.set2

import scala.collection.generic.CanBuildFrom
import as.chess.problem.piece.PositionedPiece

class VerticalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val vmt = new as.chess.problem.geom.transform.point.VerticalMirrorTransformer(boardWidth, boardHeight)

  def apply(set: Set[PositionedPiece])(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]): Set[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(vmt(pp.x, pp.y), pp.piece))(setBuilder)
}
