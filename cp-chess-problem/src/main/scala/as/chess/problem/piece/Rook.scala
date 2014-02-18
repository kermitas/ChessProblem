package as.chess.problem.piece

import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }

object Rook {
  final val pieceName = "rook"
  final val rook = new Rook
}

class Rook protected extends as.chess.classic.piece.Rook with Piece {

  override def getUnsafeFieldsForPosition(x: Int, y: Int, width: Int, height: Int) = {
    val positionModificators = UnsafePositionsGenerators.generatePositionModificatorsAsPlusSign(x, y, width, height)
    createUnsafePosition(x, y, 0, positionModificators)
  }

  override def toString = getClass.getSimpleName
}