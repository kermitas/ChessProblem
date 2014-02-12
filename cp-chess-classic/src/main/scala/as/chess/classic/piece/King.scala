package as.chess.classic.piece

object King {
  val king = new King
}

class King protected extends Piece {
  override def equals(piece: Piece): Boolean = piece == King.king //piece.isInstanceOf[King]
}