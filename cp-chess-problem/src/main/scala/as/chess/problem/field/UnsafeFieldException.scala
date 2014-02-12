package as.chess.problem.field

class UnsafeFieldException(x: Int, y: Int) extends RuntimeException(s"Position ($x, $y) is not safe")