package as.chess.problem.piece

import as.chess.problem.geom.Position

object PositionedPiece {

  def generatePositionedPiecesStream(piece: Piece, positions: Stream[Position]): Stream[PositionedPiece] = {
    positions match {
      case position #:: restOfPositions ⇒ new PositionedPiece(position.x, position.y, piece) #:: generatePositionedPiecesStream(piece, restOfPositions)
      case _                            ⇒ Stream.empty
    }
  }
}

class PositionedPiece(x: Int, y: Int, val piece: Piece) extends Position(x, y) {

  def this(xy: (Int, Int), piece: Piece) = this(xy._1, xy._2, piece)

  override def equals(arg: Any): Boolean = {
    if (arg.isInstanceOf[PositionedPiece]) {
      val pp = arg.asInstanceOf[PositionedPiece]
      x == pp.x && y == pp.y && piece == pp.piece
    } else {
      super.equals(arg)
    }
  }

  override def toString = s"[$piece ${super.toString}]"
}
