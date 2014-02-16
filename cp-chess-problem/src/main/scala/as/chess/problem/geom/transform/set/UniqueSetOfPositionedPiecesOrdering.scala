/*
package as.chess.problem.geom.transform.set

import scala.collection.Set
import as.chess.problem.piece.PositionedPiece

object UniqueSetOfPositionedPiecesOrdering extends Ordering[Set[PositionedPiece]] {

  override def compare(a: Set[PositionedPiece], b: Set[PositionedPiece]) = {
    if (a.size == b.size) {
      if (a.subsetOf(b)) {
        0
      } else {
        1
      }
    } else {
      1
    }
  }
}
*/ 