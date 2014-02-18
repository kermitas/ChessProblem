package as.chess.problem.field

import as.chess.classic.field.{ Field, Empty }

object Safe {
  val safe = new Safe
}

class Safe protected extends Empty {
  override def equals(field: Field[_]): Boolean = field == Safe.safe
}