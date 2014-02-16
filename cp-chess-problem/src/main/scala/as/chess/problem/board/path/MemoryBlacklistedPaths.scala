/*
package as.chess.problem.board.path

import as.chess.problem.piece.PositionedPiece

class MemoryBlacklistedPaths(boardWidth: Int, boardHeight: Int) extends BlacklistedPaths(boardWidth, boardHeight) {

  override def isBlacklisted(path: List[PositionedPiece]): Boolean = get(path).isDefined

  override def blacklist(path: List[PositionedPiece]) {
    for (permutedPath ← path.permutations.toList; transformedPermutedPath ← pt.getPathTransformations(permutedPath)) getOrCreate(transformedPermutedPath)
  }
}
*/ 