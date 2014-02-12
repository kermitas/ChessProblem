package as.chess.classic.board

class BoardPositionOutOfBoundsException(x: Int, y: Int, boardWidth: Int, boardHeight: Int) extends IllegalArgumentException(s"Position ($x, $y) is outside board, board dimensions: (0 - ${boardWidth - 1} , 0 - ${boardHeight - 1}).")

