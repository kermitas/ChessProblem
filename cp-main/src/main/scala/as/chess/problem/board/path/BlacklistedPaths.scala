package as.chess.problem.board.path

import as.chess.problem.geom.PositionedPiece
import as.chess.problem.board.path.geom.transform.PathTransformer

class BlacklistedPaths(boardWidth: Int, boardHeight: Int) extends PathTrieNode(new PositionedPiece(0, 0, null)) {

  val pt = new PathTransformer(boardWidth, boardHeight)
  val pp = new PathPermutator(boardWidth, boardHeight)

  def isBlacklisted(path: List[PositionedPiece]): Boolean = get(path).isDefined

  def blacklist(path: List[PositionedPiece]) {

    for (path ← pp.getSubPathPermutations(path)) {
      for (path ← pt.getPathTransformations(path)) getOrCreate(path)
    }
  }
}
