package as.chess.problem.geom.transform.array

class VerticalAndHorizontalMirrorTransformer(boardWidth: Int, boardHeight: Int) extends Serializable {

  protected def vmt = new VerticalMirrorTransformer(boardWidth, boardHeight)
  protected def hmt = new HorizontalMirrorTransformer(boardWidth, boardHeight)

  def apply[T](input: Array[Array[T]], output: Array[Array[T]]) {
    vmt(input, output)
    hmt(output, output)
  }
}
