package as.chess.problem.piece.set2

import scala.collection.parallel.mutable.ParArray
import scala.collection.immutable.TreeSet
import scala.collection.GenSeq
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform.set.PathTransformer
import scala.collection.generic.CanBuildFrom

class MutableBlacklistedSets2(val pt: PathTransformer, var sets: ParArray[Set[PositionedPiece]], treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  val setBuilder = treeSetBuilder.asInstanceOf[CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]]

  def this(boardWidth: Int, boardHeight: Int, treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) = this(new PathTransformer(boardWidth, boardHeight), ParArray[Set[PositionedPiece]](), treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = {
    pt.getPathTransformations(set, setBuilder).find { transformedSet ⇒
      //sets.find(s ⇒ s.size == transformedSet.size && transformedSet.subsetOf(s)).isDefined
      sets.find(s ⇒ transformedSet.subsetOf(s)).isDefined
    }.isDefined
  }

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece) {
    val setToAdd = set + positionedPiece
    sets.find(s ⇒ s.size == setToAdd.size && setToAdd.subsetOf(s)).getOrElse { sets = sets :+ setToAdd }
  }

  def generateReport: String = generateReport(sets)

  protected def generateReport(sets: GenSeq[Set[PositionedPiece]]): String = {

    val sb = new StringBuilder

    sb.append(s"Blacklisted sets (count ${sets.size}):").append(System.lineSeparator)

    var i = 0
    for (set ← sets) {
      sb.append(s" - set #${i} (size ${set.size}): ${set.mkString(", ")}").append(System.lineSeparator)
      i += 1
    }

    sb.toString
  }
}