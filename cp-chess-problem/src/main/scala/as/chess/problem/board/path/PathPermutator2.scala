package as.chess.problem.board.path

import as.chess.problem.piece.PositionedPiece

object PathPermutator2 {

  def getSubPathPermutations(path: List[PositionedPiece]): List[List[PositionedPiece]] = getSubPathPermutations(path, 0)

  def getSubPathPermutations(path: List[PositionedPiece], startIndex: Int): List[List[PositionedPiece]] = {

    val paths = new scala.collection.mutable.ListBuffer[List[PositionedPiece]]

    if (startIndex >= path.size - 1) {
      paths += path
    } else {
      val firstPositionedPiece = path(startIndex)
      for (i ← startIndex + 1 until path.size; positionedPiece = path(i); if firstPositionedPiece.piece == positionedPiece.piece) {
        paths ++= getSubPathPermutations(path.updated(0, positionedPiece).updated(i, firstPositionedPiece), startIndex + 1)
      }

      paths ++= getSubPathPermutations(path, startIndex + 1)
    }

    paths.toList
  }
}
