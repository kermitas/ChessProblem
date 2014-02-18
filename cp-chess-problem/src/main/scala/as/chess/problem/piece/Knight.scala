package as.chess.problem.piece

import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }

object Knight {
  final val pieceName = "knight"
  final val knight = new Knight
}

class Knight protected extends as.chess.classic.piece.Knight with Piece {

  val positionModificators = Array[(Int, Int)]((-2, -1), (-1, -2), (2, -1), (1, -2), (-2, 1), (-1, 2), (2, 1), (1, 2))

  override def getUnsafeFieldsForPosition(x: Int, y: Int, width: Int, height: Int) = {
    val pm = filterPositionModificatorsToBoardDimenstions(x, y, positionModificators, width, height)
    createUnsafePosition(x, y, 0, pm)
  }

  override def toString = getClass.getSimpleName
}