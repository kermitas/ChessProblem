package as.chess.problem.geom.transform

sealed abstract class ClockwiseQuadrantRotation(val index: Int) extends Serializable

case object ClockwiseQuadrantRotation90 extends ClockwiseQuadrantRotation(0)
case object ClockwiseQuadrantRotation180 extends ClockwiseQuadrantRotation(1)
case object ClockwiseQuadrantRotation270 extends ClockwiseQuadrantRotation(2)
