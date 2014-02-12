package as.chess.problem.board.path

import as.chess.problem.piece.PositionedPiece

object PathTrie {
  def createPath: PathTrieNode = new PathTrieNode(new PositionedPiece(0, 0, null))
}
