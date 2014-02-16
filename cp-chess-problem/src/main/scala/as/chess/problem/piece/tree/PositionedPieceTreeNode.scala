/*
package as.chess.problem.piece.tree

import scala.collection.mutable.TreeSet
import scala.collection.{ Set ⇒ GenericSet }
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.piece.set.DistanceBasedPositionedPieceOrdering

class PositionedPieceTreeNode(val positionedPiece: PositionedPiece) extends Serializable {

  protected val nodes = TreeSet[PositionedPieceTreeNode]()(PositionedPieceTreeNodeOrdering)

  def this() = this(new PositionedPiece(0, 0, null))

  def getTreeNode(pp: PositionedPiece): Option[PositionedPieceTreeNode] = nodes.find(n ⇒ n.positionedPiece.x == pp.x && n.positionedPiece.y == pp.y && n.positionedPiece.piece == pp.piece)

  def getTreeNode(set: GenericSet[PositionedPiece]): Option[PositionedPieceTreeNode] = {

    set.headOption match {

      case Some(firstPP) ⇒ getTreeNode(firstPP) match {
        case Some(node) ⇒ node.getTreeNode(set.tail)
        case None       ⇒ None
      }

      case None ⇒ Some(this)
    }
  }

  def getOrCreate(set: GenericSet[PositionedPiece]): Option[PositionedPieceTreeNode] = {

    set.headOption match {

      case Some(firstPP) ⇒ {
        val node = getTreeNode(firstPP) match {

          case Some(node) ⇒ node

          case None ⇒ {
            val node = new PositionedPieceTreeNode(firstPP)
            nodes += node
            node
          }
        }

        node.getOrCreate(set.tail)
      }

      case None ⇒ Some(this)
    }
  }

  def getPaths: List[GenericSet[PositionedPiece]] = List[GenericSet[PositionedPiece]]()

  /*
  def getPaths: List[GenericSet[PositionedPiece]] = getPaths(scala.collection.mutable.TreeSet[PositionedPiece]()(new DistanceBasedPositionedPieceOrdering))


  protected def getPaths(abovePath: GenericSet[PositionedPiece]): List[GenericSet[PositionedPiece]] = {

    val currentPath = abovePath + positionedPiece

    if (nodes.size == 0) {
      List[GenericSet[PositionedPiece]](currentPath)
    } else {

      val paths = new scala.collection.mutable.ListBuffer[GenericSet[PositionedPiece]]

      for (pathTreeNode ← nodes) {
        paths ++= pathTreeNode.getPaths(currentPath)
      }

      paths.toList
    }
  }
  */

}
*/ 