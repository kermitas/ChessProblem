package as.chess.problem.board

import scala.collection.mutable.ListBuffer
import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.geom.Position

object BoardsGenerator {

  /**
   * Creates non blocking stream of boards.
   * Whole board will be sliced into slices (partsCount) that can be processes concurrently.
   */
  def generateBoardsStream(board: Board, pieces: Stream[Piece], partsCount: Int): Seq[Stream[Option[Board]]] = {

    val fieldsCountInEachPart = splitBoardIntoParts(board, partsCount)

    val results = ListBuffer[Stream[Option[Board]]]()

    var sum = 0
    for (fieldsCountInPart ← fieldsCountInEachPart) {
      val startPosition = getPositionFromFieldNumber(sum, board.height)
      results += generateBoardsStream(board, startPosition._1, startPosition._2, fieldsCountInPart, pieces)
      sum += fieldsCountInPart
    }

    results
  }

  /**
   * Prepare a list of fields count in each slice (part).
   */
  protected def splitBoardIntoParts(board: Board, partsCount: Int): Array[Int] = {
    val fieldsCount = board.width * board.height
    val onePartFieldsCount: Int = fieldsCount / partsCount

    val partFieldsCount = new Array[Int](partsCount)
    for (i ← 0 until partsCount) partFieldsCount(i) = onePartFieldsCount

    val toAddToLastPart = (fieldsCount - onePartFieldsCount * partsCount)

    partFieldsCount(partsCount - 1) = partFieldsCount(partsCount - 1) + toAddToLastPart

    partFieldsCount
  }

  /**
   * Translates fieldNumber (assuming that 2D board is a one long line of fields) int position on board.
   */
  private def getPositionFromFieldNumber(fieldNumber: Int, boardHeight: Int): (Int, Int) = {
    val y: Int = fieldNumber / boardHeight
    val x: Int = fieldNumber % boardHeight
    (x, y)
  }

  def generateBoardsStream(board: Board, startX: Int, startY: Int, fieldsCount: Int, pieces: Stream[Piece]): Stream[Option[Board]] = {
    if (board.getSafeFieldsCount >= pieces.length) {
      getFirstPieceFromPiecesStream(board, startX, startY, fieldsCount, pieces)
    } else {
      Stream.empty
    }
  }

  protected def getFirstPieceFromPiecesStream(board: Board, startX: Int, startY: Int, fieldsCount: Int, pieces: Stream[Piece]): Stream[Option[Board]] = {
    pieces match {

      case piece #:: restOfPieces ⇒ {
        None #:: {
          val positionedPieceStream = createPositionedPieces(board, startX, startY, fieldsCount, piece)
          generateBoards(board, positionedPieceStream, restOfPieces)
        }
      }

      case _ ⇒ Some(board) #:: Stream.empty // nothing to do, returning board with all pieces on it
    }
  }

  /**
   * For given piece create positioned pieces stream where this piece is associated with all positions where
   * we will try to put it on board.
   */
  protected def createPositionedPieces(board: Board, startX: Int, startY: Int, fieldsCount: Int, piece: Piece): Stream[PositionedPiece] = {
    val positions = Position.generatePositionsStream(startX, startY, board.width, board.height, fieldsCount)
    PositionedPiece.generatePositionedPiecesStream(piece, positions)
  }

  def generateBoards(board: Board, positionedPieceStream: Stream[PositionedPiece], restOfPieces: Stream[Piece]): Stream[Option[Board]] = {
    positionedPieceStream match {
      case positionedPiece #:: restOfPositionedPieces ⇒ putPositionedPieceOnBoard(board, restOfPieces, positionedPiece, restOfPositionedPieces)
      case _ ⇒ Stream.empty
    }
  }

  protected def putPositionedPieceOnBoard(board: Board, restOfPieces: Stream[Piece], positionedPiece: PositionedPiece, restOfPositionedPieces: Stream[PositionedPiece]): Stream[Option[Board]] = {
    board.put(positionedPiece.x, positionedPiece.y, positionedPiece.piece) match {

      case Left(e) ⇒ {
        if (restOfPositionedPieces.isEmpty) {
          // there is no more positioned pieces, nothing to do
          Stream.empty
        } else {
          // let's go horizontally and check next positioned piece
          None #:: generateBoards(board, restOfPositionedPieces, restOfPieces)
        }
      }

      case Right(nextBoard) ⇒ {

        if (restOfPieces.isEmpty) {

          // no more pieces to check, returning board with all pieces on it, then go horizontally and check next positioned piece
          Some(nextBoard) #:: generateBoards(board, restOfPositionedPieces, restOfPieces)

        } else {

          val positionOfNextPiece: (Int, Int) = getPositionOfNextPiece(positionedPiece, restOfPieces(0), board.width, board.height)

          // check if there is a work to travel horizontally
          val horizontalMove = if (restOfPositionedPieces.isEmpty) {
            Stream.empty
          } else {
            None #:: generateBoards(board, restOfPositionedPieces, restOfPieces)
          }

          // go vertically (deep search first) and horizontally (if there is anything to check)
          None #:: generateBoardsStream(nextBoard, positionOfNextPiece._1, positionOfNextPiece._2, board.width * board.height, restOfPieces) ++: horizontalMove
        }
      }
    }
  }

  /**
   * If next piece is the same as current it should start with position just after current piece.
   */
  protected def getPositionOfNextPiece(currentPositionedPiece: PositionedPiece, nextPiece: Piece, boardWidth: Int, boardHeight: Int): (Int, Int) = {
    var positionOfNextPiece: (Int, Int) = (0, 0)

    if (currentPositionedPiece.piece == nextPiece) {
      positionOfNextPiece = Position.getNextPosition(currentPositionedPiece.x, currentPositionedPiece.y, boardWidth, boardHeight) match {
        case Some(np) ⇒ np
        case None     ⇒ positionOfNextPiece // there is no next position because we will be outside of board
      }
    }

    positionOfNextPiece
  }
}