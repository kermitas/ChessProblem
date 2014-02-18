package as.chess.problem.field

import as.chess.classic.field.{ Field, Empty }

object Unsafe {
  val unsafe = new Unsafe
}

class Unsafe protected extends Empty {
  override def equals(field: Field[_]): Boolean = field == Unsafe.unsafe
}