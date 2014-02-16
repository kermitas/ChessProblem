package as.chess.problem.geom.transform.path

import as.chess.problem.piece.PositionedPiece
import as.chess.problem.geom.transform._

class PathTransformer(boardWidth: Int, boardHeight: Int) extends Serializable {

  val hmt = new HorizontalMirrorTransformer(boardWidth, boardHeight)
  val vmt = new VerticalMirrorTransformer(boardWidth, boardHeight)
  val vhmt = new VerticalAndHorizontalMirrorTransformer(boardWidth, boardHeight)
  val ct = new ClockwiseTransformer(boardWidth, boardHeight)

  def getPathTransformations(path: List[PositionedPiece]): List[List[PositionedPiece]] = {

    val paths = new scala.collection.mutable.ListBuffer[List[PositionedPiece]]

    // TODO: introduce def addIfUnique(path: List[PositionedPiece])

    def addWithVerticalMirroring(path: List[PositionedPiece]) {
      paths += path
      paths += vmt(path)
    }

    addWithVerticalMirroring(path)

    if (boardWidth == boardHeight) {
      addWithVerticalMirroring(ct(path, ClockwiseQuadrantRotation90))
      addWithVerticalMirroring(ct(path, ClockwiseQuadrantRotation180))
      addWithVerticalMirroring(ct(path, ClockwiseQuadrantRotation270))
    } else {
      paths += hmt(path)
      paths += vhmt(path)
    }

    paths.toList
  }
}
