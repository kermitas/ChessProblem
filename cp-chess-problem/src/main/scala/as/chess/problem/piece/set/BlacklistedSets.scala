package as.chess.problem.piece.set

import scala.collection.immutable.TreeSet
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform.set2.PathTransformer
import as.chess.problem.geom.transform.set2.UniqueSetOfPositionedPiecesOrdering
import scala.collection.generic.CanBuildFrom

class MutableBlacklistedSets(boardWidth: Int, boardHeight: Int)(treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  var bl = new BlacklistedSets(boardWidth, boardHeight)(treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = bl.isBlacklisted(set)

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece) {
    bl = bl.blacklist(set, positionedPiece)
  }

  def printSets() = bl.printSets()
}

class BlacklistedSets(val pt: PathTransformer, val sets: TreeSet[Set[PositionedPiece]])(treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) {

  //val setBuilder = treeSetBuilder.asInstanceOf[CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]]
  val setBuilder: scala.collection.generic.CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]] = collection.immutable.TreeSet.newCanBuildFrom[PositionedPiece](new DistanceBasedPositionedPieceOrdering).asInstanceOf[scala.collection.generic.CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]]
  //val pt = new PathTransformer(boardWidth, boardHeight)

  //val setsBuilder: scala.collection.generic.CanBuildFrom[TreeSet[Set[PositionedPiece]], Set[PositionedPiece], TreeSet[Set[PositionedPiece]]] = TreeSet.newCanBuildFrom[Set[PositionedPiece]](new UniqueSetOfPositionedPiecesOrdering)

  def this(boardWidth: Int, boardHeight: Int)(treeSetBuilder: CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]]) = this(new PathTransformer(boardWidth, boardHeight), TreeSet[Set[PositionedPiece]]()(new UniqueSetOfPositionedPiecesOrdering))(treeSetBuilder)

  def isBlacklisted(set: Set[PositionedPiece]): Boolean = {
    sets.find { s ⇒

      //set.subsetOf(s)
      //s.subsetOf(set)

      //if (s.size == 6 && s.size == set.size) {
      //  println(s"Our ${s.mkString(", ")}")
      //  println(s"Ask ${set.mkString(", ")}")
      //  println(s"= result ${set.subsetOf(s)}")
      //}

      if (s.size == set.size && set.subsetOf(s))
        true
      else
        false

    }.isDefined
  }

  //def isBlacklisted(set: Set[PositionedPiece]): Boolean = sets.contains(set)

  def printSets() {
    printSets(sets)
  }

  def printSets(sets: TreeSet[Set[PositionedPiece]]) {

    println(s"BlacklistedSets.printSets: ---- blacklisted sets (${sets.size}):")

    var i = 0
    for (set ← sets) {
      println(s"BlacklistedSets.printSets: set #${i} (size ${set.size}): ${set.mkString(", ")}")
      i += 1
    }
  }

  def blacklist(set: Set[PositionedPiece], positionedPiece: PositionedPiece): BlacklistedSets = {

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

    /*
    for (transformedSet ← pt.getPathTransformations(set)) {
      newSets = newSets + transformedSet
    }
    */

    //val newSets = (sets - set.init) + set
    /*
    val newSets = removeSetAndGetNewSets(set.init) match {
      case Some((newSets, s)) ⇒ newSets + set
      case None               ⇒ sets + set
    }
    */

    new BlacklistedSets(pt, newSets)(treeSetBuilder)
  }

  /*
  protected def removeSetAndGetNewSets(set: Set[PositionedPiece]): Option[(TreeSet[Set[PositionedPiece]], Set[PositionedPiece])] = {
    getSet(set) match {
      case Some(s) ⇒ Some((sets - s, s))
      case None    ⇒ None
    }
  }
  */

  /*
  protected def getSet(set: Set[PositionedPiece]): Option[Set[PositionedPiece]] = {
    sets.find { s ⇒
      if (s.size == set.size) {
        s.subsetOf(set)
      } else {
        false
      }
    }
  }
  */

}

/*
object UniqueSetOfPositionedPiecesOrdering extends Ordering[Set[PositionedPiece]] {

  override def compare(a: Set[PositionedPiece], b: Set[PositionedPiece]) = {
    if (a.size == b.size) {
      if (a.subsetOf(b)) {
        0
      } else {
        1
      }
    } else {
      1
    }
  }
}
*/ 