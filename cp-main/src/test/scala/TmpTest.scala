import as.chess.problem.piece.set.DistanceBasedPositionedPieceOrdering
import as.chess.problem.piece.{ Rook, Queen, King, PositionedPiece }
import org.scalatest._
import scala.collection.immutable.TreeSet

class TmpTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("abc", AbcTestTag) {
    abc
    0 should be(0)
  }

  def abc {
    /*
    val ts = mutable.TreeSet[Int]()(new AbcOrdering)

    ts += 1
    ts += 2
    ts += 3

    println(ts.mkString(","))

    val ts1 = ts.map(_ * 2)

    ts1 += 1
    ts1 += 7

    println(ts1)

    val ts2 = ts1 + 8

    println(ts2)
    */

    val treeSetBuilder: scala.collection.generic.CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]] = TreeSet.newCanBuildFrom[PositionedPiece](new DistanceBasedPositionedPieceOrdering(3))

    var bl = new as.chess.problem.piece.set.BlacklistedSets(3, 3)(treeSetBuilder)

    val set1 = scala.collection.immutable.Set[PositionedPiece]()

    bl = bl.blacklist(set1, new PositionedPiece(0, 0, King.king))
    printSets(bl.sets)

    //val set2 = scala.collection.immutable.Set[PositionedPiece](
    //  new PositionedPiece(0, 0, King.king),
    //  new PositionedPiece(1, 1, King.king))

    bl = bl.blacklist(set1, new PositionedPiece(1, 1, King.king))
    printSets(bl.sets)

    val set3 = scala.collection.immutable.Set[PositionedPiece](
      new PositionedPiece(1, 1, King.king))

    println(bl.isBlacklisted(set3))

    val set4 = scala.collection.immutable.Set[PositionedPiece](
      new PositionedPiece(2, 0, King.king),
      new PositionedPiece(1, 1, King.king))

    println(set4.size + " " + bl.isBlacklisted(set4))

    //val treeSetBuilder: scala.collection.generic.CanBuildFrom[scala.collection.immutable.TreeSet[PositionedPiece], PositionedPiece, scala.collection.immutable.TreeSet[PositionedPiece]] = scala.collection.immutable.TreeSet.newCanBuildFrom[PositionedPiece](new as.chess.problem.piece.set.DistanceBasedPositionedPieceOrdering)

    var set5: scala.collection.immutable.TreeSet[PositionedPiece] = scala.collection.immutable.TreeSet[PositionedPiece](new PositionedPiece(1, 1, King.king))(new as.chess.problem.piece.set.DistanceBasedPositionedPieceOrdering(3))
    println(s"set5 = ${set5.mkString(", ")}")

    set5 = set5 + new PositionedPiece(0, 0, King.king)
    println(s"set5 = ${set5.mkString(", ")}")

    set5 = set5 + new PositionedPiece(3, 3, King.king)
    println(s"set5 = ${set5.mkString(", ")}")

    set5 = set5 + new PositionedPiece(2, 2, King.king)
    println(s"set5 = ${set5.mkString(", ")}")

    //var set6: scala.collection.immutable.TreeSet[PositionedPiece] = set5.map(pp ⇒ new PositionedPiece(pp.x * 2, pp.y * 2, pp.piece))
    var set6 = set5.map(pp ⇒ new PositionedPiece(pp.x * 2, pp.y * 2, pp.piece))(treeSetBuilder)
    println(s"set6 = ${set6.mkString(", ")}")

    set6 = set6 + new PositionedPiece(5, 5, King.king)
    println(s"set6 = ${set6.mkString(", ")}")

    set6 = set6.filterNot(pp ⇒ pp.x > 4)
    println(s"set6 = ${set6.mkString(", ")}")

    /*
    val set3 = scala.collection.immutable.Set[PositionedPiece](
      new PositionedPiece(1, 1, Queen.queen),
      new PositionedPiece(0, 0, King.king))

    bl = bl.blacklist(set3)
    printSets(bl.sets)
    */
    /*
        val set4 = scala.collection.immutable.Set[PositionedPiece](
          new PositionedPiece(0, 0, King.king),
          new PositionedPiece(1, 1, King.king),
          new PositionedPiece(2, 2, King.king))

        bl = bl.blacklist(set4)
        printSets(bl.sets)
        */
  }

  def printSets(sets: scala.collection.immutable.TreeSet[Set[PositionedPiece]]) {

    println(s"-- Blacklisted sets (${sets.size}):")

    var i = 0
    for (set ← sets) {
      println(s"Set #${i}: ${set.mkString(", ")}")
      i += 1
    }
  }
}

/*
class AbcOrdering extends Ordering[Int] {
  override def compare(a: Int, b: Int) = {
    val result = if (a < b)
      -1
    else if (a > b)
      1
    else
      0

    println(s"Ordering $a $b result=$result")

    result
  }
}
*/ 