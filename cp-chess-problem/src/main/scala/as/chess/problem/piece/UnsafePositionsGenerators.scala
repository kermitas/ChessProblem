package as.chess.problem.piece

object UnsafePositionsGenerators {

  def generatePositionModificatorsAsPlusSign(centerX: Int, centerY: Int, width: Int, height: Int): Array[(Int, Int)] = {

    val list = new scala.collection.mutable.ListBuffer[(Int, Int)]

    for (xx ← centerX + 1 until width) list += Tuple2(xx - centerX, 0)
    for (xx ← 0 until centerX) list += Tuple2(xx - centerX, 0)

    for (yy ← centerY + 1 until height) list += Tuple2(0, yy - centerY)
    for (yy ← 0 until centerY) list += Tuple2(0, yy - centerY)

    list.toArray
  }

  def generatePositionModificatorsAsCrossSign(centerX: Int, centerY: Int, width: Int, height: Int): Array[(Int, Int)] = {

    val list = new scala.collection.mutable.ListBuffer[(Int, Int)]

    var xx = centerX + 1
    var yy = centerY + 1

    while (xx < width && yy < height) {
      list += Tuple2(xx - centerX, yy - centerY)
      xx += 1
      yy += 1
    }

    xx = centerX - 1
    yy = centerY + 1

    while (xx >= 0 && yy < height) {
      list += Tuple2(xx - centerX, yy - centerY)
      xx -= 1
      yy += 1
    }

    xx = centerX - 1
    yy = centerY - 1

    while (xx >= 0 && yy >= 0) {
      list += Tuple2(xx - centerX, yy - centerY)
      xx -= 1
      yy -= 1
    }

    xx = centerX + 1
    yy = centerY - 1

    while (xx < width && yy >= 0) {
      list += Tuple2(xx - centerX, yy - centerY)
      xx += 1
      yy -= 1
    }

    list.toArray
  }
}
