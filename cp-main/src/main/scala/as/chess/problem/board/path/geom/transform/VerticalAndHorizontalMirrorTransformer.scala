package as.chess.problem.board.path.geom.transform

import as.chess.problem.geom.PositionedPiece

class VerticalAndHorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val vhmt = new as.chess.problem.geom.transform.point.VerticalAndHorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(path: List[PositionedPiece]): List[PositionedPiece] = path.map(pp ⇒ new PositionedPiece(vhmt(pp.x, pp.y), pp.piece))
}
