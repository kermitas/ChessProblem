/*
package as.chess.problem.geom.transform.set

import scala.collection.{ Set ⇒ GenericSet }
import as.chess.problem.piece.PositionedPiece

class VerticalAndHorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val vhmt = new as.chess.problem.geom.transform.point.VerticalAndHorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(set: GenericSet[PositionedPiece]): GenericSet[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(vhmt(pp.x, pp.y), pp.piece))
}
*/ 