package as.chess.problem.board.path.geom.transform

import as.chess.problem.geom.PositionedPiece

class VerticalMirrorTransformer(val boardWidth: Int, val boardHeight: Int) extends Serializable {

  val vmt = new as.chess.problem.geom.transform.point.VerticalMirrorTransformer(boardWidth, boardHeight)

  def apply(path: List[PositionedPiece]): List[PositionedPiece] = path.map(pp ⇒ new PositionedPiece(vmt(pp.x, pp.y), pp.piece))
}