package as.chess.problem.geom.transform.set

import scala.collection.{ Set ⇒ GenericSet }
import as.chess.problem.piece.PositionedPiece

class VerticalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val vmt = new as.chess.problem.geom.transform.point.VerticalMirrorTransformer(boardWidth, boardHeight)

  def apply(set: GenericSet[PositionedPiece]): GenericSet[PositionedPiece] = set.map(pp ⇒ new PositionedPiece(vmt(pp.x, pp.y), pp.piece))
}
