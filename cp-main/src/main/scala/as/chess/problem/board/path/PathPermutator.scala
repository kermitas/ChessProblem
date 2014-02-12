package as.chess.problem.board.path

import as.chess.problem.geom.PositionedPiece

class PathPermutator(boardWidth: Int, boardHeight: Int) extends Serializable {

  def getSubPathPermutations(path: List[PositionedPiece]): List[List[PositionedPiece]] = getSubPathPermutations(path, 0)

  def getSubPathPermutations(path: List[PositionedPiece], startIndex: Int): List[List[PositionedPiece]] = {

    val paths = new scala.collection.mutable.ListBuffer[List[PositionedPiece]]

    var i = startIndex
    while (i < path.length) {
      val sameCount = getSameElementsLength(path, i)

      if (sameCount > 1) {

        val permutatedSubPaths = permutateSubPath(path, i, sameCount)

        for (permutatedSubPath ← permutatedSubPaths) {
          paths ++= getSubPathPermutations(permutatedSubPath, i + sameCount)
        }

        i = path.length
      } else {
        i += 1
      }
    }

    if (paths.size == 0) {
      paths += path
    }

    paths.toList
  }

  protected def getSameElementsLength(path: List[PositionedPiece], startIndex: Int): Int = {
    if (startIndex < path.length) {
      val firstElement = path(startIndex)
      var result = 0
      var i = startIndex
      while (i < path.length && path(i).piece == firstElement.piece) {
        result += 1
        i += 1
      }
      result
    } else
      0
  }

  protected def permutateSubPath(path: List[PositionedPiece], startIndex: Int, count: Int): List[List[PositionedPiece]] = {

    val prefix = path.take(startIndex)
    val suffix = path.drop(startIndex + count)
    val paths = new scala.collection.mutable.ListBuffer[List[PositionedPiece]]

    for (permutatedSubPath ← path.drop(startIndex).take(count).permutations) paths += prefix ++ permutatedSubPath ++ suffix

    paths.toList
  }
}
