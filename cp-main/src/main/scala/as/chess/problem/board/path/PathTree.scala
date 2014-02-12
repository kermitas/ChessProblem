package as.chess.problem.board.path

import as.chess.problem.piece.PositionedPiece

object PathTree {
  def createPath: PathTreeNode = new PathTreeNode(new PositionedPiece(0, 0, null))
}
