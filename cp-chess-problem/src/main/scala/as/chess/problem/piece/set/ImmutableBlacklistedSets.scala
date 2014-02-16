package as.chess.problem.piece.set

import scala.collection.immutable.TreeSet
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform.set.PathTransformer
import scala.collection.generic.CanBuildFrom

class ImmutableBlacklistedSets(val pt: PathTransformer, val sets: TreeSet[Set[PositionedPiece]], treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  val setBuilder = treeSetBuilder.asInstanceOf[CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]]

  def this(boardWidth: Int, boardHeight: Int, treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) = this(new PathTransformer(boardWidth, boardHeight), TreeSet[Set[PositionedPiece]]()(new UniqueSetOfPositionedPiecesOrdering), treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = sets.find { s ⇒ s.size == set.size && set.subsetOf(s) }.isDefined

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece): ImmutableBlacklistedSets = {
    var newSets = sets
    for (transformedSet ← pt.getPathTransformations(set + positionedPiece, setBuilder)) newSets = newSets + transformedSet
    new ImmutableBlacklistedSets(pt, newSets, treeSetBuilder)
  }

  def generateReport: String = generateReport(sets)

  protected def generateReport(sets: TreeSet[Set[PositionedPiece]]): String = {

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