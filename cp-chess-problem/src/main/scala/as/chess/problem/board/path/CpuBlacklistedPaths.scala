/*
package as.chess.problem.board.path

import as.chess.problem.piece.PositionedPiece

class CpuBlacklistedPaths(boardWidth: Int, boardHeight: Int) extends BlacklistedPaths(boardWidth, boardHeight) {

  override def isBlacklisted(path: List[PositionedPiece]): Boolean = {
    path.permutations.toList.find { permutedPath ⇒
      pt.getPathTransformations(permutedPath).find { transformedPermutedPath ⇒
        get(transformedPermutedPath).isDefined
      }.isDefined
    }.isDefined
  }

  override def blacklist(path: List[PositionedPiece]) {
    getOrCreate(path)
  }
}
*/ 