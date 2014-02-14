package as.chess.problem.board.path

import as.chess.problem.geom.transform.path.PathTransformer
import as.chess.problem.piece.PositionedPiece

object BlacklistedPaths {
  def createPathsBlacklister(boardWidth: Int, boardHeight: Int, ifTrueThenMemoryBasedIfFalseThenCPUBased: Boolean) = {
    if (ifTrueThenMemoryBasedIfFalseThenCPUBased)
      new MemoryBlacklistedPaths(boardWidth, boardHeight)
    else
      new CpuBlacklistedPaths(boardWidth, boardHeight)
  }

  def createMemoryBasedPathsBlacklister(boardWidth: Int, boardHeight: Int) = createPathsBlacklister(boardWidth, boardHeight, true)

  def createCpuBasedPathsBlacklister(boardWidth: Int, boardHeight: Int) = createPathsBlacklister(boardWidth, boardHeight, false)
}

abstract class BlacklistedPaths(boardWidth: Int, boardHeight: Int) extends PathTreeNode(new PositionedPiece(0, 0, null)) {

  val pt = new PathTransformer(boardWidth, boardHeight)

  def isBlacklisted(path: List[PositionedPiece]): Boolean

  def blacklist(path: List[PositionedPiece])
}

class MemoryBlacklistedPaths(boardWidth: Int, boardHeight: Int) extends BlacklistedPaths(boardWidth, boardHeight) {

  override def isBlacklisted(path: List[PositionedPiece]): Boolean = get(path).isDefined

  override def blacklist(path: List[PositionedPiece]) {
    for (permutedPath ← path.permutations.toList; transformedPermutedPath ← pt.getPathTransformations(permutedPath)) getOrCreate(transformedPermutedPath)
  }
}

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