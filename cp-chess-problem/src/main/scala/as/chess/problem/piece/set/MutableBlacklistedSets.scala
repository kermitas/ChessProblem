package as.chess.problem.piece.set

import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.TreeSet
import as.chess.problem.piece.PositionedPiece

/**
 * Not thread safe!
 */
class MutableBlacklistedSets(boardWidth: Int, boardHeight: Int, treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  protected var bl = new ImmutableBlacklistedSets(boardWidth, boardHeight, treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = bl.isBlacklisted(set)

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece) {
    bl = bl.blacklist(set, positionedPiece)
  }

  def generateReport: String = bl.generateReport
}