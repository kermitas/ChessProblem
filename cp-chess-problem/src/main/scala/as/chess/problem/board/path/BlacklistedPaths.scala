package as.chess.problem.board.path

import as.chess.problem.geom.transform.path.PathTransformer
import as.chess.problem.piece.PositionedPiece

object BlacklistedPaths {

  sealed abstract class WorkMode(val name: String) extends Serializable
  case object MemoryWorkMode extends WorkMode("memory")
  case object CpuWorkMode extends WorkMode("cpu")

  def getWorkMode(workModeName: String): WorkMode = workModeName.toLowerCase match {
    case MemoryWorkMode.name ⇒ MemoryWorkMode
    case CpuWorkMode.name    ⇒ CpuWorkMode
  }

  def createPathsBlacklister(boardWidth: Int, boardHeight: Int, workMode: WorkMode) = {
    workMode match {
      case MemoryWorkMode ⇒ new MemoryBlacklistedPaths(boardWidth, boardHeight)
      case CpuWorkMode    ⇒ new CpuBlacklistedPaths(boardWidth, boardHeight)
    }
  }
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