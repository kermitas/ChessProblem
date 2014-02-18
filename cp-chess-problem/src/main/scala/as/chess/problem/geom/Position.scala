package as.chess.problem.geom

class Position(val x: Int, val y: Int) extends Serializable {
  override def toString = s"($x,$y)"
}

object Position {

  def generatePositionsStream(startX: Int, startY: Int, width: Int, height: Int): Stream[Position] = {
    generatePositionsStream(startX, startY, width, height, width * height)
  }

  /**
   * Generate stream of all positions on board with given dimensions. Will generate only positions count defined in 'count'.
   *
   * Stop generating on board end or count limit reached - whichever comes first.
   */
  def generatePositionsStream(startX: Int, startY: Int, width: Int, height: Int, count: Int): Stream[Position] = {
    generatePositions(startX, startY, width, height, 0, count)
  }

  protected def generatePositions(startX: Int, startY: Int, width: Int, height: Int, current: Int, count: Int): Stream[Position] = {
    if (current < count) {

      new Position(startX, startY) #:: {
        val x = startX + 1
        if (x >= width) {
          val y = startY + 1
          if (y >= height) {
            Stream.empty
          } else {
            generatePositions(0, y, width, height, current + 1, count)
          }
        } else {
          generatePositions(x, startY, width, height, current + 1, count)
        }
      }

    } else {
      Stream.empty
    }
  }

  /**
   * Returns next position if there is any of none if next position will be outside of board.
   */
  def getNextPosition(startX: Int, startY: Int, width: Int, height: Int): Option[(Int, Int)] = {
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
