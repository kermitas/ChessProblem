package as.chess.problem.piece

import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }
import as.chess.classic.board.{ FieldTranslator }
import as.chess.problem.field.Unsafe
import as.chess.problem.board.{ Board ⇒ ProblemBoard }

object Piece {

  def apply(commandLineArguments: Array[String], startIndex: Int): Stream[Piece] = {
    val pieces = for (index ← Range(startIndex, commandLineArguments.length, 2); piece ← parsePiece(commandLineArguments, index)) yield piece

    pieces.sortWith(getTopOffenderOrder(_) < getTopOffenderOrder(_)).toStream
  }

  def parsePiece(commandLineArguments: Array[String], index: Int): Seq[Piece] = {
    val count = Integer.parseInt(commandLineArguments(index))
    require(count >= 1, "Piece count can not be less than 1")
    val piece = Piece(commandLineArguments(index + 1))
    for (i ← 0 until count) yield piece
  }

  def getTopOffenderOrder(piece: Piece): Int = piece match {
    case Queen.queen           ⇒ 1
    case Bishop.bishop         ⇒ 2
    case Rook.rook             ⇒ 3
    case King.king             ⇒ 4
    case Knight.knight         ⇒ 5
    case LightFlyer.lightFlyer ⇒ 6 // just for test purposes
  }

  def apply(name: String): Piece = name.toLowerCase match {
    case King.pieceName       ⇒ King.king
    case Queen.pieceName      ⇒ Queen.queen
    case Bishop.pieceName     ⇒ Bishop.bishop
    case Rook.pieceName       ⇒ Rook.rook
    case Knight.pieceName     ⇒ Knight.knight
    case LightFlyer.pieceName ⇒ LightFlyer.lightFlyer // just for test purposes
  }
}

trait Piece { self: ClassicPiece ⇒

  def getUnsafeFieldsForPosition(x: Int, y: Int, width: Int, height: Int): Stream[(Int, Int, FieldTranslator)]

  protected def filterPositionModificatorsToBoardDimenstions(x: Int, y: Int, positionModificators: Array[(Int, Int)], width: Int, height: Int): Array[(Int, Int)] = {
    positionModificators.filter { positionModifier ⇒
      val xx = x + positionModifier._1
      val yy = y + positionModifier._2
      xx >= 0 && xx < width && yy >= 0 && yy < height
    }
  }

  protected def createUnsafePosition(putX: Int, putY: Int, index: Int, positionModificators: Array[(Int, Int)]): Stream[(Int, Int, FieldTranslator)] = {
    val xx = putX + positionModificators(index)._1
    val yy = putY + positionModificators(index)._2

    (xx, yy, ProblemBoard.getFieldTranslatorsForThisBoard(Unsafe.unsafe)) #:: {
      if (index == positionModificators.length - 1)
        Stream.empty
      else
        createUnsafePosition(putX, putY, index + 1, positionModificators)
    }
  }
}