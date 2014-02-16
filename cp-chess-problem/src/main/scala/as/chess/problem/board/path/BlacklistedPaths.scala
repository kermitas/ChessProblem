/*
package as.chess.problem.board.path

import as.chess.problem.geom.transform.path.PathTransformer
import as.chess.problem.piece.PositionedPiece

object BlacklistedPaths {

  sealed abstract class WorkStrategy(val name: String) extends Serializable
  case object MemoryWorkStrategy extends WorkStrategy("memory")
  case object CpuWorkStrategy extends WorkStrategy("cpu")

  def getWorkStrategy(strategyName: String): WorkStrategy = strategyName.toLowerCase match {
    case MemoryWorkStrategy.name ⇒ MemoryWorkStrategy
    case CpuWorkStrategy.name    ⇒ CpuWorkStrategy
  }

  def createPathsBlacklister(boardWidth: Int, boardHeight: Int, workStrategy: WorkStrategy) = workStrategy match {
    case MemoryWorkStrategy ⇒ new MemoryBlacklistedPaths(boardWidth, boardHeight)
    case CpuWorkStrategy    ⇒ new CpuBlacklistedPaths(boardWidth, boardHeight)
  }
}

abstract class BlacklistedPaths(boardWidth: Int, boardHeight: Int) extends PathTreeNode(new PositionedPiece(0, 0, null)) {

  val pt = new PathTransformer(boardWidth, boardHeight)

  def isBlacklisted(path: List[PositionedPiece]): Boolean
  def blacklist(path: List[PositionedPiece])
}
*/
