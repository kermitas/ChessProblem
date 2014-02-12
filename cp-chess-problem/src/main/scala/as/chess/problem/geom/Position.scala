package as.chess.problem.geom

object Position {

  def generatePositionsStream(startX: Int, startY: Int, width: Int, height: Int): Stream[Position] = {
    new Position(startX, startY) #:: {
      var x = startX + 1
      if (x >= width) {
        x = 0
        val y = startY + 1
        if (y >= height) {
          Stream.empty
        } else {
          generatePositionsStream(x, y, width, height)
        }
      } else {
        generatePositionsStream(x, startY, width, height)
      }
    }
  }
}

class Position(val x: Int, val y: Int) extends Serializable {
  override def toString = s"($x,$y)"
}