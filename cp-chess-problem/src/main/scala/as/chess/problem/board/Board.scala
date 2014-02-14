package as.chess.problem.board

import as.chess.classic.board.{ Board ⇒ ClassicBoard, _ }
import as.chess.classic.field.{ Field ⇒ ClassicField, Occupied, LocatedField }
import as.chess.classic.piece.{ Piece ⇒ ClassicPiece }

import as.chess.problem.piece.{ Piece ⇒ ProblemPiece, TakingOtherPieceException }
import as.chess.problem.field.{ Safe, Unsafe, UnsafeFieldException }
import as.chess.problem.geom.transform.array._
import as.chess.problem.geom.transform.ClockwiseQuadrantRotation

object Board {

  def apply(commandLineArguments: Array[String], startIndex: Int): Board = apply(Integer.parseInt(commandLineArguments(startIndex)), Integer.parseInt(commandLineArguments(startIndex + 1)))

  def apply(width: Int, height: Int): Board = new Board(width, height)

  def fieldIsUnsafe(fieldThatWillBePut: ClassicField[ClassicPiece]): FieldTranslator = {
    case LocatedField(x, y, Unsafe.unsafe) ⇒ fieldThatWillBePut match {
      case Safe.safe     ⇒ Right(Safe.safe)
      case Unsafe.unsafe ⇒ Right(Unsafe.unsafe)
      case _             ⇒ Left(new UnsafeFieldException(x, y))
    }
  }

  def takingOthersPiece(fieldThatWillBePut: ClassicField[ClassicPiece]): FieldTranslator = {
    case LocatedField(x, y, o: Occupied[_]) if fieldThatWillBePut == Unsafe.unsafe ⇒ Left(new TakingOtherPieceException(x, y, o.piece))
  }

  /**
   * Rules (on the left current field value):
   * - Empty field => on this board we should have Safe fields in place of Empty (but if found then behave as Safe)
   * - Safe field => allow to put Occupied OR Safe OR Unsafe
   * - Unsafe field => allow to put Safe, Unsafe
   * - Occupied field => if you are thing to put Unsafe then TakingOtherPieceException will be raised, otherwise does not allow to put anything with OccupiedFieldException
   */
  def getFieldTranslatorsForThisBoard(fieldThatWillBePut: ClassicField[ClassicPiece]): FieldTranslator = ClassicBoard.fieldIsOutsideBoard orElse fieldIsUnsafe(fieldThatWillBePut) orElse takingOthersPiece(fieldThatWillBePut) orElse ClassicBoard.fieldIsOccupied orElse ClassicBoard.fieldToPut(fieldThatWillBePut)
}

class Board(array: Array[Array[ClassicField[ClassicPiece]]]) extends ClassicBoard(array) {

  import Board._

  val vmt = new VerticalMirrorTransformer(width, height)
  val hmt = new HorizontalMirrorTransformer(width, height)
  val vhmt = new VerticalAndHorizontalMirrorTransformer(width, height)
  val ct = new ClockwiseTransformer(width, height)

  def this(width: Int, height: Int, fieldCreator: ⇒ ClassicField[ClassicPiece] = Safe.safe) = this(Array.fill[ClassicField[ClassicPiece]](height, width)(fieldCreator))

  def getSafeFieldsCount = getFieldsCount(_ == Safe.safe)

  override def put(x: Int, y: Int, piece: ClassicPiece): Either[Exception, Board] = put(x, y, new Occupied(piece))

  override def put(x: Int, y: Int, field: ClassicField[ClassicPiece]): Either[Exception, Board] = put(x, y, getFieldTranslatorsForThisBoard(field))

  override def put(x: Int, y: Int, singleFieldCreator: FieldTranslator): Either[Exception, Board] = {
    super.put(x, y, singleFieldCreator) match {
      case Left(e)         ⇒ Left(e)
      case Right(newBoard) ⇒ Right(new Board(newBoard.getArray))
    }
  }

  override def put(multipleFieldsCreator: Stream[(Int, Int, FieldTranslator)]): Either[Exception, Board] = {
    super.put(multipleFieldsCreator) match {
      case Left(e)         ⇒ Left(e)
      case Right(newBoard) ⇒ Right(new Board(newBoard.getArray))
    }
  }

  def put(x: Int, y: Int, piece: ProblemPiece): Either[Exception, Board] = {

    put(x, y, piece.asInstanceOf[ClassicPiece]) match {

      case Left(e) ⇒ Left(e)

      case Right(newBoard) ⇒ {
        val unsafePositions = piece.getUnsafeFieldsForPosition(x, y, width, height)
        newBoard.put(unsafePositions)
      }
    }
  }

  def equals(b: Board): Boolean = {
    val result = if (width == b.width && height == b.height) {
      (0 until height).find { y ⇒
        (0 until width).find { x ⇒
          //!array(y)(x).equals(b.array(y)(x))

          val f1 = array(y)(x)
          val f2 = b.array(y)(x)

          //val result =

          /*
          val bo = if (f1.isInstanceOf[Occupied[_]] && f2.isInstanceOf[Occupied[_]])
            f1.equals(f2)
          else
            false
          */

          val isO1 = f1.isInstanceOf[Occupied[_]]
          val isO2 = f2.isInstanceOf[Occupied[_]]

          val bo = if (isO1 && isO2)
            f1.equals(f2)
          else if (isO1 || isO2)
            false
          else
            true

          //!result

          //println(s"$f1 $f2 $bo")

          !bo
        }.isDefined
      }.isEmpty
    } else {
      false
    }

    //println(as.chess.problem.drawer.AsciiDrawer.draw(this.asInstanceOf[as.chess.problem.board.Board]))
    //println(as.chess.problem.drawer.AsciiDrawer.draw(b.asInstanceOf[as.chess.problem.board.Board]))
    //println(s"========= $result")

    result
  }

  def mirrorHorizontally: Board = {
    val newBoard = new Board(width, height)
    hmt(array, newBoard.array)
    newBoard
  }

  def mirrorVertically: Board = {
    val newBoard = new Board(width, height)
    vmt(array, newBoard.array)
    newBoard
  }

  def mirrorVerticallyAndHorizontally: Board = {
    val newBoard = new Board(width, height)
    vhmt(array, newBoard.array)
    newBoard
  }

  def rotateClockwise(clockwiseQuadrantRotation: ClockwiseQuadrantRotation): Board = {
    val newBoard = new Board(height, width)
    ct(array, newBoard.array, clockwiseQuadrantRotation)
    newBoard
  }
}
