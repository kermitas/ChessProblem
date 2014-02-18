package as.chess.problem.geom

object Position2 {

  def generatePositionsStream(startX: Int, startY: Int, width: Int, height: Int): Stream[Position] = {
    generatePositionsStream(startX, startY, width, height, width * height)
  }

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