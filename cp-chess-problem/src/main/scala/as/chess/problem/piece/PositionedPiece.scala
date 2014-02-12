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

  override def toString = s"[$piece ${super.toString}]"
}
