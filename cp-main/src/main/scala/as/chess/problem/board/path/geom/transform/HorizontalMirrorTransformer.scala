package as.chess.problem.board.path.geom.transform

import as.chess.problem.geom.PositionedPiece

class HorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val hmt = new as.chess.problem.geom.transform.point.HorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(path: List[PositionedPiece]): List[PositionedPiece] = path.map(pp ⇒ new PositionedPiece(hmt(pp.x, pp.y), pp.piece))
}
