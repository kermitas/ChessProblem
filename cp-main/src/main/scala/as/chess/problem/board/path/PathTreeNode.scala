package as.chess.problem.board.path

import as.chess.problem.piece.PositionedPiece

class PathTreeNode(val positionedPiece: PositionedPiece) extends Serializable {
  val nodes = new scala.collection.mutable.ListBuffer[PathTreeNode]

  def get(pp: PositionedPiece): Option[PathTreeNode] = nodes.find(n ⇒ n.positionedPiece.x == pp.x && n.positionedPiece.y == pp.y && n.positionedPiece.piece == pp.piece)

  def get(path: List[PositionedPiece]): Option[PathTreeNode] = {
    path.headOption match {

      case Some(firstPP) ⇒ get(firstPP) match {
        case Some(node) ⇒ node.get(path.tail)
        case None       ⇒ None
      }

      case None ⇒ Some(this)
    }
  }

  def getOrCreate(path: List[PositionedPiece]): Option[PathTreeNode] = {
    path.headOption match {

      case Some(firstPP) ⇒ get(firstPP) match {

        case Some(node) ⇒ node.getOrCreate(path.tail)

        case None ⇒ {
          val node = new PathTreeNode(firstPP)
          nodes += node
          node.getOrCreate(path.tail)
        }
      }

      case None ⇒ Some(this)
    }
  }
}
