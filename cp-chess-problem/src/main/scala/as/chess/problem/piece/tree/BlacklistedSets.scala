/*
package as.chess.problem.piece.tree

import scala.collection.{ Set ⇒ GenericSet }
import scala.collection.mutable.TreeSet
import as.chess.problem.geom.transform.set.PathTransformer
import as.chess.problem.piece.PositionedPiece

class BlacklistedSets(boardWidth: Int, boardHeight: Int) extends PositionedPieceTreeNode {

  val pt = new PathTransformer(boardWidth, boardHeight)

  def isBlacklisted(set: GenericSet[PositionedPiece]): Boolean = getTreeNode(set).isDefined

  def blacklist(set: GenericSet[PositionedPiece]) {

    //println(s"permutations: ${set.toList.permutations.mkString(",\n")}")

    val toBlacklist = TreeSet[GenericSet[PositionedPiece]]()(as.chess.problem.geom.transform.set.UniqueSetOfPositionedPiecesOrdering)

    println(s"input \n${set.mkString(",")}")

    for (permutedList ← set.toList.permutations) {

      //println(s"--- permuted \n${permutedList.mkString(",\n")}")

      for (transformedPermutedSet ← pt.getPathTransformations(permutedList.toSet)) {
        //println(s"permuted&translated \n${transformedPermutedSet.mkString(",\n")}")
        toBlacklist += transformedPermutedSet
      }
    }

    println(s"toBlacklist: \n${toBlacklist.mkString(",\n")}")

    toBlacklist.foreach(getOrCreate)

    /*
    val set = GenericSet[PositionedPiece](
      new PositionedPiece(0, 0, King.king),
      new PositionedPiece(1, 1, Queen.queen),
      new PositionedPiece(2, 2, Rook.rook))

    println(s"translations: ${pt.getPathTransformations(set).mkString(",\n")}")

    val ts1 = scala.collection.mutable.TreeSet[GenericSet[PositionedPiece]]()(as.chess.problem.geom.transform.set.TransformedPathsOrdering)

    ts1 += set

    val set1 = GenericSet[PositionedPiece](
      new PositionedPiece(1, 1, Queen.queen),
      new PositionedPiece(0, 0, King.king),
      new PositionedPiece(2, 2, Rook.rook))

    ts1 += set1

    println(s"ts1: \n${ts1.mkString(",\n")}")

    println(s"contains: \n${set1.contains(new PositionedPiece(1, 1, Queen.queen))}")
    */

    //for (permutedList ← set.toList.permutations; transformedPermutedSet ← pt.getPathTransformations(permutedList.toSet)) getOrCreate(transformedPermutedSet)
  }
}

*/ 