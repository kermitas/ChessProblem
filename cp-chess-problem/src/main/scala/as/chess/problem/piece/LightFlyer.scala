package as.chess.problem.piece

import as.chess.classic.board.{ FieldTranslator }
import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }

object LightFlyer {
  final val pieceName = "lightflyer"
  final val lightFlyer = new LightFlyer
}

/**
 * Piece just for testing purposes.
 *
 * Has no power to take/hit other piece.
 */
class LightFlyer protected extends ClassicPiece with Piece {

  override def getUnsafeFieldsForPosition(x: Int, y: Int, width: Int, height: Int): Stream[(Int, Int, FieldTranslator)] = Stream.empty

  override def toString = getClass.getSimpleName
}