package as.chess.problem.piece.set2

import scala.collection.parallel.mutable.ParArray
import scala.collection.immutable.TreeSet
import scala.collection.GenSeq
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform.set.PathTransformer
import scala.collection.generic.CanBuildFrom

object MutableBlacklistedSets3 {

  def createArrayOfParArrayOfPositionedPiecesSets(size: Int): Array[ParArray[Set[PositionedPiece]]] = {
    val array = new Array[ParArray[Set[PositionedPiece]]](size)
    for (i ← 0 until size) array(i) = ParArray[Set[PositionedPiece]]()
    array
  }
}

class MutableBlacklistedSets3(val pt: PathTransformer, val sets: Array[ParArray[Set[PositionedPiece]]], treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  val setBuilder = treeSetBuilder.asInstanceOf[CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]]

  def this(boardWidth: Int, boardHeight: Int, treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) = this(new PathTransformer(boardWidth, boardHeight), MutableBlacklistedSets3.createArrayOfParArrayOfPositionedPiecesSets(boardWidth * boardHeight), treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = {
    sets(set.size).find { s ⇒ set.subsetOf(s) }.isDefined
  }

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece) {
    for (transformedSet ← pt.getPathTransformations(set + positionedPiece, setBuilder)) {
      sets(transformedSet.size).find(s ⇒ transformedSet.subsetOf(s)).getOrElse { sets(transformedSet.size) = sets(transformedSet.size) :+ transformedSet }
    }
  }

  def generateReport: String = {
    val sb = new StringBuilder

    sb.append(s"Blacklisted sets (count ${sets.size}):").append(System.lineSeparator)

    var i = 0
    for (parArray ← sets) {
      for (set ← parArray) {
        sb.append(s" - set #${i} (size ${set.size}): ${set.mkString(", ")}").append(System.lineSeparator)
        i += 1
      }
    }

    sb.toString
  }
}