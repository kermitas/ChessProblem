package as.chess.problem.geom

object Position {

  def generatePositionsStream(width: Int, height: Int): Stream[Position] = generatePositionsStream(0, 0, width, height)

  def generatePositionsStream(startX: Int, startY: Int, width: Int, height: Int): Stream[Position] = {
    normalizeStartPosition(startX, startY, width, height) match {
      case Some((x, y)) ⇒ generatePositions(x, y, width, height)
      case None         ⇒ Stream.empty
    }
  }

  protected def generatePositions(startX: Int, startY: Int, width: Int, height: Int): Stream[Position] = {
    new Position(startX, startY) #:: {
      val x = startX + 1
      if (x >= width) {
        val y = startY + 1
        if (y >= height) {
          Stream.empty
        } else {
          generatePositions(0, y, width, height)
        }
      } else {
        generatePositions(x, startY, width, height)
      }
    }
  }

  protected def normalizeStartPosition(startX: Int, startY: Int, width: Int, height: Int): Option[(Int, Int)] = {
    if (startX >= width) {
      val y = startY + 1

      if (y >= height)
        None
      else
        Some((0, y))
    } else
      Some((startX, startY))
  }
}

class Position(val x: Int, val y: Int) extends Serializable {
  override def toString = s"($x,$y)"
}