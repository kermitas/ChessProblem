package as.chess.problem.geom.transform.set

import scala.collection.{ Set ⇒ GenericSet }
import scala.collection.mutable.TreeSet
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform._

class PathTransformer(boardWidth: Int, boardHeight: Int) extends Serializable {

  val hmt = new HorizontalMirrorTransformer(boardWidth, boardHeight)
  val vmt = new VerticalMirrorTransformer(boardWidth, boardHeight)
  val vhmt = new VerticalAndHorizontalMirrorTransformer(boardWidth, boardHeight)
  val ct = new ClockwiseTransformer(boardWidth, boardHeight)

  def getPathTransformations(set: GenericSet[PositionedPiece]): TreeSet[GenericSet[PositionedPiece]] = {

    val paths = new TreeSet[GenericSet[PositionedPiece]]()(UniqueSetOfPositionedPiecesOrdering)

    def addWithVerticalMirroring(set: GenericSet[PositionedPiece]) {
      paths += set
      paths += vmt(set)
    }

    addWithVerticalMirroring(set)

    if (boardWidth == boardHeight) {
      addWithVerticalMirroring(ct(set, ClockwiseQuadrantRotation90))
      addWithVerticalMirroring(ct(set, ClockwiseQuadrantRotation180))
      addWithVerticalMirroring(ct(set, ClockwiseQuadrantRotation270))
    } else {
      paths += hmt(set)
      paths += vhmt(set)
    }

    paths
  }
}
