package as.chess.problem.board.path

import as.chess.problem.piece.PositionedPiece

class PathTrieNode(val positionedPiece: PositionedPiece) extends Serializable {
  val nodes = new scala.collection.mutable.ListBuffer[PathTrieNode]

  def get(pp: PositionedPiece): Option[PathTrieNode] = nodes.find(n ⇒ n.positionedPiece.x == pp.x && n.positionedPiece.y == pp.y && n.positionedPiece.piece == pp.piece)

  def get(path: List[PositionedPiece]): Option[PathTrieNode] = {
    path.headOption match {

      case Some(firstPP) ⇒ get(firstPP) match {
        case Some(node) ⇒ node.get(path.tail)
        case None       ⇒ None
      }

      case None ⇒ Some(this)
    }
  }

  def getOrCreate(path: List[PositionedPiece]): Option[PathTrieNode] = {
    path.headOption match {

      case Some(firstPP) ⇒ get(firstPP) match {

        case Some(node) ⇒ node.getOrCreate(path.tail)

        case None ⇒ {
          val node = new PathTrieNode(firstPP)
          nodes += node
          node.getOrCreate(path.tail)
        }
      }

      case None ⇒ Some(this)
    }
  }
}
