package as.chess.problem.piece

import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }

object Bishop {
  final val pieceName = "bishop"
  final val bishop = new Bishop
}

class Bishop protected extends as.chess.classic.piece.Bishop with Piece {

  override def getUnsafeFieldsForPosition(x: Int, y: Int, width: Int, height: Int) = {
    val positionModificators = UnsafePositionsGenerators.generatePositionModificatorsAsCrossSign(x, y, width, height)
    createUnsafePosition(x, y, 0, positionModificators)
  }

  override def toString = getClass.getSimpleName
}