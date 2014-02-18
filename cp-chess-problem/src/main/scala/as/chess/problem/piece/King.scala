package as.chess.problem.piece

import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }

object King {
  final val pieceName = "king"
  final val king = new King
}

class King protected extends as.chess.classic.piece.King with Piece {

  val positionModificators = Array[(Int, Int)]((-1, -1), (0, -1), (1, -1), (-1, 1), (0, 1), (1, 1), (-1, 0), (1, 0))

  override def getUnsafeFieldsForPosition(x: Int, y: Int, width: Int, height: Int) = {
    val pm = filterPositionModificatorsToBoardDimenstions(x, y, positionModificators, width, height)
    createUnsafePosition(x, y, 0, pm)
  }

  override def toString = getClass.getSimpleName
}