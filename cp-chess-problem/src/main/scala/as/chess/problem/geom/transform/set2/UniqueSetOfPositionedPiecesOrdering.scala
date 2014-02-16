package as.chess.problem.geom.transform.set2

import as.chess.problem.piece.PositionedPiece

class UniqueSetOfPositionedPiecesOrdering extends Ordering[Set[PositionedPiece]] {

  override def compare(a: Set[PositionedPiece], b: Set[PositionedPiece]) = {

    if (a.size == b.size && a.subsetOf(b)) {
      0
    } else if (a.size < b.size) {
      -1
    } else {
      1
    }

    /*
    if (a.size == b.size && a.subsetOf(b)) {
      if (a.subsetOf(b)) {
        0
      } else {
        1
      }
    } else {
      1
    }
    */
  }
}