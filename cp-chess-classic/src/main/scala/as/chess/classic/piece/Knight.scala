package as.chess.classic.piece

object Knight {
  val knight = new Knight
}

class Knight protected extends Piece {
  override def equals(piece: Piece): Boolean = piece == Knight.knight //piece.isInstanceOf[Knight]
}