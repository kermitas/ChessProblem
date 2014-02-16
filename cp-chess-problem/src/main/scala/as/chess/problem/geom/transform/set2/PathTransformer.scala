package as.chess.problem.geom.transform.set2

import scala.collection.immutable.{ Set, TreeSet }
import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform._
import scala.collection.generic.CanBuildFrom

class PathTransformer(boardWidth: Int, boardHeight: Int) extends Serializable {

  val hmt = new HorizontalMirrorTransformer(boardWidth, boardHeight)
  val vmt = new VerticalMirrorTransformer(boardWidth, boardHeight)
  val vhmt = new VerticalAndHorizontalMirrorTransformer(boardWidth, boardHeight)
  val ct = new ClockwiseTransformer(boardWidth, boardHeight)

  def getPathTransformations(set: Set[PositionedPiece])(setBuilder: CanBuildFrom[Set[PositionedPiece], PositionedPiece, Set[PositionedPiece]]): Set[Set[PositionedPiece]] = {

    var paths = new TreeSet[Set[PositionedPiece]]()(new UniqueSetOfPositionedPiecesOrdering)

    def addWithVerticalMirroring(set: Set[PositionedPiece]) {
      paths = paths + set
      paths = paths + vmt(set)(setBuilder)
    }

    addWithVerticalMirroring(set)

    if (boardWidth == boardHeight) {
      addWithVerticalMirroring(ct(set, ClockwiseQuadrantRotation90)(setBuilder))
      addWithVerticalMirroring(ct(set, ClockwiseQuadrantRotation180)(setBuilder))
      addWithVerticalMirroring(ct(set, ClockwiseQuadrantRotation270)(setBuilder))
    } else {
      paths = paths + hmt(set)(setBuilder)
      paths = paths + vhmt(set)(setBuilder)
    }

    paths
  }
}
