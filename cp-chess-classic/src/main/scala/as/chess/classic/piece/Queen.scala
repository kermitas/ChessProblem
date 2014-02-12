package as.chess.classic.piece

object Queen {
  val queen = new Queen
}

class Queen protected extends Piece {
  override def equals(piece: Piece): Boolean = piece == Queen.queen //piece.isInstanceOf[Queen]
}