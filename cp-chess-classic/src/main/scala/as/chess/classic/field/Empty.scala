package as.chess.classic.field

object Empty {
  val empty = new Empty
}

class Empty protected extends Field[Nothing] {
  override def equals(field: Field[_]): Boolean = field == Empty.empty
}