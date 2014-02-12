package as.chess.classic.piece

object Rook {
  val rook = new Rook
}

class Rook protected extends Piece {
  override def equals(piece: Piece): Boolean = piece == Rook.rook //piece.isInstanceOf[Rook]
}