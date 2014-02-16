package as.chess.problem.piece.tree

object PositionedPieceTreeNodeOrdering extends Ordering[PositionedPieceTreeNode] {
  override def compare(a: PositionedPieceTreeNode, b: PositionedPieceTreeNode) = {
    if (a.positionedPiece.equals(b)) {
      0
    } else {
      val distanceOfA = Math.sqrt(a.positionedPiece.x * a.positionedPiece.x + a.positionedPiece.y * a.positionedPiece.y)
      val distanceOfB = Math.sqrt(b.positionedPiece.x * b.positionedPiece.x + b.positionedPiece.y * b.positionedPiece.y)

      if (distanceOfA < distanceOfB) {
        -1
      } else {
        1
      }
    }

  }
}