package as.chess.classic.piece

object Bishop {
  val bishop = new Bishop
}

class Bishop protected extends Piece {
  override def equals(piece: Piece): Boolean = piece == Bishop.bishop //piece.isInstanceOf[Bishop]
}