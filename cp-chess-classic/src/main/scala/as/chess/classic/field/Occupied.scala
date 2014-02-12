package as.chess.classic.field

import scala.reflect.ClassTag
import as.chess.classic.piece.Piece

class Occupied[P <: Piece: ClassTag](val piece: P) extends Field[P] {
  override def equals(field: Field[_]): Boolean = field match {
    case o: Occupied[_] ⇒ piece.equals(o.piece.asInstanceOf[Piece])
    case _              ⇒ false
  }
}

