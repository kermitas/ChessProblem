package as.chess.problem.geom.transform.path

import as.chess.problem.piece.PositionedPiece

class VerticalAndHorizontalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val vhmt = new as.chess.problem.geom.transform.point.VerticalAndHorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply(path: List[PositionedPiece]): List[PositionedPiece] = path.map(pp ⇒ new PositionedPiece(vhmt(pp.x, pp.y), pp.piece))
}
