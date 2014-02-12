package as.chess.problem.piece

class TakingOtherPieceException[P <: as.chess.classic.piece.Piece](takeeX: Int, takeeY: Int, takee: P) extends RuntimeException(s"Your action will cause taking $takee on position ($takeeX, $takeeY).")