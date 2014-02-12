package as.chess.classic.piece

abstract class Piece extends Serializable {
  def equals(piece: Piece): Boolean
}