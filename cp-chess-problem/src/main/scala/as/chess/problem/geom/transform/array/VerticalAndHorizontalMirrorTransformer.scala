package as.chess.problem.geom.transform.array

import scala.reflect.ClassTag

class VerticalAndHorizontalMirrorTransformer(boardWidth: Int, boardHeight: Int) extends Serializable {

  protected def vmt = new VerticalMirrorTransformer(boardWidth, boardHeight)
  protected def hmt = new HorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply[T: ClassTag](input: Array[Array[T]], output: Array[Array[T]]) {

    val tmpArray = Array.ofDim[T](boardHeight, boardWidth)

    vmt(input, tmpArray)
    hmt(tmpArray, output)
  }
}
