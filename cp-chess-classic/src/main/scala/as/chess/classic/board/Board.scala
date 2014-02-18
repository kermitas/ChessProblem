package as.chess.classic.board

import as.chess.classic.field._
import as.chess.classic.piece.Piece

object Board {

  /**
   * Parse command line arguments and create board.
   */
  def apply(commandLineArguments: Array[String], startIndex: Int) = new Board(Integer.parseInt(commandLineArguments(startIndex)), Integer.parseInt(commandLineArguments(startIndex + 1)))

  /**
   * Just an marker field. See put method for usage.
   */
  case class SpecialFieldThatIsOutsideOfBoard(width: Int, height: Int) extends Field[Piece] {
    override def equals(field: Field[_]): Boolean = field.isInstanceOf[SpecialFieldThatIsOutsideOfBoard]
  }

  val fieldIsOutsideBoard: FieldTranslator = {
    case LocatedField(x, y, SpecialFieldThatIsOutsideOfBoard(width, height)) ⇒ Left(new BoardPositionOutOfBoundsException(x, y, width, height))
  }

  val fieldIsOccupied: FieldTranslator = {
    case LocatedField(x, y, o: Occupied[_]) ⇒ Left(new OccupiedFieldException[Piece](x, y, o.piece))
  }

  def fieldToPut(field: Field[Piece]): FieldTranslator = {
    case _ ⇒ new Right(field)
  }

  /**
   * Rules (on the left: current field value, on the right: behaviour):
   * - bad x,y => exception
   * - Empty field => allow to put Occupied
   * - Occupied field => does not allow to put anything
   */
  def getFieldTranslatorsForThisBoard(fieldThatWillBePut: Field[Piece]): FieldTranslator = fieldIsOutsideBoard orElse fieldIsOccupied orElse fieldToPut(fieldThatWillBePut)
}

/**
 * ---------------------
 * Board geometry:
 *
 * +----+----+----+
 * | ~~ | ~~ | ~~ |
 * +----+----+----+
 * | ~~ | Ki | ~~ |
 * +----+----+----+
 * | ~~ | ~~ | ~~ |
 * +----+----+----+
 *
 * Above board has King on (1,1). Rest of fields is marked as unsafe.
 *
 * +----+----+----+
 * | Ki | ~~ |    |
 * +----+----+----+
 * | ~~ | ~~ |    |
 * +----+----+----+
 * |    |    |    |
 * +----+----+----+
 *
 * Above board has King on (0,0). Fields (1,0), (0,1) and (1,1) are marked as unsafe. Rest of fields is empty.
 *
 * ---------------------
 */
class Board(protected val array: Array[Array[Field[Piece]]]) extends Serializable {

  import Board._

  require(array.length >= 1, "Board height can not be less than 1.")
  require(array(0).length >= 1, "Board width can not be less than 1.")

  val width = array(0).length
  val height = array.length

  def this(width: Int, height: Int, fieldCreator: ⇒ Field[Piece] = Empty.empty) = this(Array.fill[Field[Piece]](height, width)(fieldCreator))

  def getTotalFieldsCount: Long = width * height

  def foreachField(f: Field[Piece] ⇒ Unit): Unit = for (y ← 0 until height; x ← 0 until width) f(array(y)(x))

  def getFieldsCount(classifier: Field[Piece] ⇒ Boolean): Int = {
    var count = 0
    foreachField(field ⇒ if (classifier(field)) count += 1)
    count
  }

  def getEmptyFieldsCount = getFieldsCount(_ == Empty)

  def getOccupiedFieldsCount = getFieldsCount(_.isInstanceOf[Occupied[_]])

  def isValidBoardPosition(x: Int, y: Int): Boolean = (x >= 0 && x < width) && (y >= 0 && y < height)

  /**
   * Be careful: function f can have side effect (for example when modify array)
   */
  protected def executeOnValidPosition[A](x: Int, y: Int, f: ⇒ A): Either[Exception, A] = {
    if (isValidBoardPosition(x, y)) {
      Right(f)
    } else {
      Left(new BoardPositionOutOfBoundsException(x, y, width, height))
    }
  }

  def get(x: Int, y: Int): Either[Exception, Field[Piece]] = executeOnValidPosition[Field[Piece]](x, y, array(y)(x))

  /**
   * Danger: should not be used
   */
  def getArray = array

  def put(x: Int, y: Int, piece: Piece): Either[Exception, Board] = put(x, y, new Occupied(piece))

  def put(x: Int, y: Int, field: Field[Piece]): Either[Exception, Board] = put(x, y, getFieldTranslatorsForThisBoard(field))

  def put(multipleFieldsCreator: Stream[(Int, Int, FieldTranslator)]): Either[Exception, Board] = put(multipleFieldsCreator, this)

  protected def put(multipleFieldsCreator: Stream[(Int, Int, FieldTranslator)], board: Board): Either[Exception, Board] = {

    multipleFieldsCreator match {

      case positionAndFieldCreator #:: restOfTheStream ⇒ {
        put(positionAndFieldCreator._1, positionAndFieldCreator._2, positionAndFieldCreator._3) match {
          case Left(e)      ⇒ Left(e)
          case Right(board) ⇒ board.put(restOfTheStream, board)
        }
      }

      case _ ⇒ Right(board)
    }
  }

  /**
   * If (x,y) is outside of board instance of SpecialFieldThatIsOutsideOfBoard will be passed in LocatedField
   * to singleFieldCreator to indicate this situation.
   */
  def put(x: Int, y: Int, singleFieldCreator: FieldTranslator): Either[Exception, Board] = {

    val currentField = if (isValidBoardPosition(x, y))
      array(y)(x)
    else
      new SpecialFieldThatIsOutsideOfBoard(width, height)

    singleFieldCreator(new LocatedField(x, y, currentField)) match {

      case Left(e) ⇒ Left(e)

      case Right(newField) ⇒ {
        val newBoard = cloneBoard
        newBoard(y)(x) = newField
        Right(new Board(newBoard))
      }
    }
  }

  def equals(b: Board): Boolean = {
    if (width == b.width && height == b.height) {
      (0 until height).find { y ⇒
        (0 until width).find { x ⇒

          val f1 = array(y)(x)
          val f2 = b.array(y)(x)

          val isO1 = f1.isInstanceOf[Occupied[_]]
          val isO2 = f2.isInstanceOf[Occupied[_]]

          val r = if (isO1 && isO2)
            f1.equals(f2)
          else if (isO1 || isO2)
            false
          else
            true

          !r
        }.isDefined
      }.isEmpty
    } else {
      false
    }
  }

  //def equals(b: Board): Boolean = throw new RuntimeException("Noo!")

  /*
  def equals(b: Board): Boolean = {
    if (width == b.width && height == b.height) {
      (0 until height).find { y ⇒
        (0 until width).find { x ⇒
          !array(y)(x).equals(b.array(y)(x))

          //array(y)(x) match {
          //  case o: Occupied[_] =>

          //}

        }.isDefined
      }.isEmpty
    } else {
      false
    }
  }
  */

  /*
  def equals(b: Board): Boolean = {
    if (width == b.width && height == b.height) {
      (0 until height).find { y ⇒
        (0 until width).find { x ⇒
          !array(y)(x).equals(b.array(y)(x))
        }.isDefined
      }.isEmpty
    } else {
      false
    }
  }
  */

  /*
  def equals(b: Board): Boolean = {
    val result = if (width == b.width && height == b.height) {
      (0 until height).find { y ⇒
        (0 until width).find { x ⇒
          //!array(y)(x).equals(b.array(y)(x))

          val f1 = array(y)(x)
          val f2 = b.array(y)(x)

          val bo: Boolean = f1.equals(f2)

          println(s"$f1 $f2 $bo")

          !bo

        }.isDefined
      }.isEmpty
    } else {
      false
    }

    println(as.chess.problem.drawer.AsciiDrawer.draw(this.asInstanceOf[as.chess.problem.board.Board]))
    println(as.chess.problem.drawer.AsciiDrawer.draw(b.asInstanceOf[as.chess.problem.board.Board]))
    println(s"========= $result")

    result
  }
  */

  protected def cloneBoard: Array[Array[Field[Piece]]] = {
    val newBoardArray = Array.ofDim[Field[Piece]](height, width)
    for (y ← 0 until height; x ← 0 until width) newBoardArray(y)(x) = array(y)(x)
    newBoardArray
  }
}