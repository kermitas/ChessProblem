package as.chess.problem.piece.set

import as.chess.problem.piece.PositionedPiece

/**
 * Eliminate duplications and keeps order of PositionedPiece in set (for example TreeSet[PositionedPiece]).
 *
 * Order is based on distance from point (0,0), then if equal on lower y or lower x, example:
 * [(0,0) King] [(1,1) Queen] [(2,2) Bishop] <-- King is the closes to (0,0) so it goes first, then (1,1) and (2,2)
 */
class DistanceBasedPositionedPieceInSetOrdering(boardHeight: Int) extends Ordering[PositionedPiece] {

  override def compare(a: PositionedPiece, b: PositionedPiece) = {

    if (a.x == b.x && a.y == b.y && a.piece == b.piece) {
      0
    } else {

      val distanceOfA = a.y * boardHeight + a.x
      val distanceOfB = b.y * boardHeight + b.x

      /**
       * can also be calculated like that (in this case we don't need boardHeight as an constructor argument)
       */
      //val distanceOfA = Math.sqrt(a.x * a.x + a.y * a.y)
      //val distanceOfB = Math.sqrt(b.x * b.x + b.y * b.y)

      if (distanceOfA < distanceOfB) {
        -1
      } else if (distanceOfA > distanceOfB) {
        1
      } else if (a.y < b.y) {
        -1
      } else if (a.x < b.x) {
        -1
      } else {
        1
      }

    }

  }
}