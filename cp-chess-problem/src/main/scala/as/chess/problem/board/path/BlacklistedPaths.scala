package as.chess.problem.board.path

import as.chess.problem.geom.transform.path.PathTransformer
import as.chess.problem.piece.PositionedPiece

class BlacklistedPaths(boardWidth: Int, boardHeight: Int) extends PathTreeNode(new PositionedPiece(0, 0, null)) {

  val pt = new PathTransformer(boardWidth, boardHeight)

  def isBlacklisted(path: List[PositionedPiece]): Boolean = get(path).isDefined

  def blacklist(path: List[PositionedPiece]) {
    for (permutedPath ← path.permutations.toList; transformedPermutedPath ← pt.getPathTransformations(permutedPath)) getOrCreate(transformedPermutedPath)
  }
}
