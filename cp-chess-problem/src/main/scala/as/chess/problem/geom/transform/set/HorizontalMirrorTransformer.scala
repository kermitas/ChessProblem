package as.chess.problem.geom.transform.set

import scala.collection.{ Set ⇒ GenericSet }
import as.chess.problem.piece.PositionedPiece

class HorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val hmt = new as.chess.problem.geom.transform.point.HorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(set: GenericSet[PositionedPiece]): GenericSet[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(hmt(pp.x, pp.y), pp.piece))
}
