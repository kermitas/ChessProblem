/*
package as.chess.problem.piece.tree

import as.chess.problem.piece.PositionedPiece

class PositionedPieceTree(val positionedPiece: PositionedPiece) extends Serializable {

  protected val nodes = new scala.collection.mutable.TreeSet[PositionedPieceTree]

  def this() = this(0, 0, null)

  def get(pp: PositionedPiece): Option[PositionedPieceTree] = nodes.find(n ⇒ n.positionedPiece.x == pp.x && n.positionedPiece.y == pp.y && n.positionedPiece.piece == pp.piece)

  def get(path: List[PositionedPiece]): Option[PositionedPieceTree] = {

    path.headOption match {

      case Some(firstPP) ⇒ get(firstPP) match {
        case Some(node) ⇒ node.get(path.tail)
        case None       ⇒ None
      }

      case None ⇒ Some(this)
    }
  }

  def getOrCreate(path: List[PositionedPiece]): Option[PositionedPieceTree] = {

    path.headOption match {

      case Some(firstPP) ⇒ {
        val node = get(firstPP) match {

          case Some(node) ⇒ node

          case None ⇒ {
            val node = new PositionedPieceTree(firstPP)
            nodes += node
            node
          }
        }

        node.getOrCreate(path.tail)
      }

      case None ⇒ Some(this)
    }
  }

  def getPaths: List[List[PositionedPiece]] = getPaths(List[PositionedPiece]())

  protected def getPaths(abovePath: List[PositionedPiece]): List[List[PositionedPiece]] = {

    val currentPath = abovePath :+ positionedPiece

    if (nodes.size == 0) {
      List[List[PositionedPiece]](currentPath)
    } else {

      val paths = new scala.collection.mutable.ListBuffer[List[PositionedPiece]]

      for (pathTreeNode ← nodes) {
        paths ++= pathTreeNode.getPaths(currentPath)
      }

      paths.toList
    }
  }
}
*/