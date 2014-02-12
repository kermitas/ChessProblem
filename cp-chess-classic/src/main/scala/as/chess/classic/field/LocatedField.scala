package as.chess.classic.field

import as.chess.classic.piece.Piece

case class LocatedField(x: Int, y: Int, field: Field[Piece]) extends Serializable
