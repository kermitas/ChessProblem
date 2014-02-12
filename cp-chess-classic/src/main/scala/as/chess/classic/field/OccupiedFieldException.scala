package as.chess.classic.field

import as.chess.classic.piece.Piece

class OccupiedFieldException[P <: Piece](x: Int, y: Int, originalPiece: P) extends RuntimeException(s"Field ($x, $y) is occupied by ${originalPiece}.")
