package as.chess.problem.piece

import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }

object Queen {
  final val pieceName = "queen"
  final val queen = new Queen
}

class Queen protected extends as.chess.classic.piece.Queen with Piece {

  override def getUnsafeFieldsForPosition(x: Int, y: Int, width: Int, height: Int) = {
    val positionModificatorsAsCross = UnsafePositionsGenerators.generatePositionModificatorsAsCrossSign(x, y, width, height)
    val positionModificatorsAsPlus = UnsafePositionsGenerators.generatePositionModificatorsAsPlusSign(x, y, width, height)
    createUnsafePosition(x, y, 0, Array.concat(positionModificatorsAsCross, positionModificatorsAsPlus))
  }

  override def toString = getClass.getSimpleName
}