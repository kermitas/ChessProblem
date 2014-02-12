package as.chess.classic

import as.chess.classic.field.{ LocatedField, Field }
import as.chess.classic.piece.Piece

package object board {
  type FieldTranslator = PartialFunction[LocatedField, Either[Exception, Field[Piece]]]
}
