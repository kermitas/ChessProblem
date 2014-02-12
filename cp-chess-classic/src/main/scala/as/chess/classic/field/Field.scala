package as.chess.classic.field

import scala.reflect.ClassTag
import as.chess.classic.piece.Piece

abstract class Field[+P <: Piece: ClassTag] extends Serializable {
  def equals(field: Field[_]): Boolean
}