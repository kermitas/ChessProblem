package as.chess.problem.piece.set2

import scala.collection.parallel.mutable.ParArray
import scala.collection.immutable.TreeSet
import scala.collection.GenSeq
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform.set.PathTransformer
import scala.collection.generic.CanBuildFrom

class MutableBlacklistedSets(val pt: PathTransformer, var sets: ParArray[Set[PositionedPiece]], treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  val setBuilder = treeSetBuilder.asInstanceOf[CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]]

  def this(boardWidth: Int, boardHeight: Int, treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) = this(new PathTransformer(boardWidth, boardHeight), ParArray[Set[PositionedPiece]](), treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = sets.find { s ⇒ s.size == set.size && set.subsetOf(s) }.isDefined

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece) {
    for (transformedSet ← pt.getPathTransformations(set + positionedPiece, setBuilder)) {
      sets.find(s ⇒ s.size == transformedSet.size && transformedSet.subsetOf(s)).getOrElse { sets = sets :+ transformedSet }

      /*
      sets.find(s ⇒ s.size == set.size && set.subsetOf(s)) match {

        case Some(foundSimilarSet) ⇒

        case None ⇒ {
          println(s"adding ${transformedSet.mkString(", ")}")
          sets = sets :+ transformedSet
        }
      }*/
    }

    //println(s"After blacklisting ${set.mkString(", ")} accumulated sests:")
    //println(generateReport)
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