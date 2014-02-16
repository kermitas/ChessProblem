package as.chess.problem.piece.set

import as.chess.problem.piece.PositionedPiece

/**
 * Eliminate duplications and keeps order in a set of sets (for example TreeSet[Set[PositionedPiece]]).
 *
 * Order is based on a size of set in descending order, example:
 * [(0,0) King] [(1,1) Queen] [(2,2) Bishop] <-- this set contains 3 PositionedPiece elements
 * [(3,3) Rook] [(4,4) Knight] <---------------- this set contains 2 PositionedPiece elements, that why is second one
 */
class UniqueSetOfPositionedPiecesOrdering extends Ordering[Set[PositionedPiece]] {

  override def compare(a: Set[PositionedPiece], b: Set[PositionedPiece]) = {

    if (a.size == b.size && a.subsetOf(b)) {
      0
    } else if (a.size < b.size) {
      1
    } else {
      -1
    }
  }
}