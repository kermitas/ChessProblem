package as.chess.problem.board.path

import as.chess.problem.geom.PositionedPiece

object PathTrie {
  def createPath: PathTrieNode = new PathTrieNode(new PositionedPiece(0, 0, null))
}
