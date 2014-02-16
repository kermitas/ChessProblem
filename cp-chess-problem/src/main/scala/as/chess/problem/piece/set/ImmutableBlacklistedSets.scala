package as.chess.problem.piece.set

import scala.collection.immutable.TreeSet
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform.set.PathTransformer
import as.chess.problem.geom.transform.set.UniqueSetOfPositionedPiecesOrdering
import scala.collection.generic.CanBuildFrom

class ImmutableBlacklistedSets(val pt: PathTransformer, val sets: TreeSet[Set[PositionedPiece]], treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  val setBuilder = treeSetBuilder.asInstanceOf[CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]]

  def this(boardWidth: Int, boardHeight: Int, treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) = this(new PathTransformer(boardWidth, boardHeight), TreeSet[Set[PositionedPiece]]()(new UniqueSetOfPositionedPiecesOrdering), treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = {
    sets.find { s ⇒
      if (s.size == set.size && set.subsetOf(s))
        true
      else
        false

    }.isDefined
  }

  def generateReport: String = generateReport(sets)

  protected def generateReport(sets: TreeSet[Set[PositionedPiece]]): String = {

    val sb = new StringBuilder

    sb.append(s"BlacklistedSets.printSets: ---- blacklisted sets (${sets.size}):").append(System.lineSeparator)

    var i = 0
    for (set ← sets) {
      sb.append(s"BlacklistedSets.printSets: set #${i} (size ${set.size}): ${set.mkString(", ")}").append(System.lineSeparator)
      i += 1
    }

    sb.toString
  }

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece): ImmutableBlacklistedSets = {

    var newSets = sets

    //println(s"BlacklistedSets.blacklist: will blacklist: ${set.mkString(", ")}")
    //printSets(newSets)

    //println(s"BlacklistedSets.blacklist: transformed set: ${pt.getPathTransformations(set)(setBuilder)}")

    //for (transformedSet ← pt.getPathTransformations(set)(setBuilder)) {
    //println(s"BlacklistedSets.blacklist: transformed short set: ${transformedSet.mkString(", ")}")
    //  newSets = newSets.filterNot(set ⇒ set.size == transformedSet.size && transformedSet.subsetOf(set))
    //}

    for (transformedSet ← pt.getPathTransformations(set + positionedPiece)(setBuilder)) {

      //println(s"BlacklistedSets.blacklist: transformed long set: ${transformedSet.mkString(", ")}")

      //println(s"BlacklistedSets.blacklist: transformed set: ${transformedSet.mkString(", ")}")

      //println(s"BlacklistedSets.blacklist: removing from sets transformed set init: ${transformedSet.init.mkString(", ")}")

      //val transformedSetInit = transformedSet.init
      //newSets = newSets.filterNot(set ⇒ set.size == transformedSetInit.size && transformedSetInit.subsetOf(set))

      //newSets = newSets - transformedSet.init

      //println(s"BlacklistedSets.blacklist: sets after removing transformed set init:")
      //printSets(newSets)

      //println(s"BlacklistedSets.blacklist: adding transformed set: ${transformedSet.mkString(", ")}")

      newSets = newSets + transformedSet

      //println(s"BlacklistedSets.blacklist: sets after adding transformed set:")
      //printSets(newSets)
    }

    //println(s"BlacklistedSets.blacklist: after blacklisting ${(set + positionedPiece).mkString(", ")} we are keeping:")
    //printSets(newSets)

    new ImmutableBlacklistedSets(pt, newSets, treeSetBuilder)
  }
}