package as.chess.problem.board

import scala.collection.mutable.ListBuffer
import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.geom.Position2

object BoardsGenerator {

  def generateBoardsStream(board: ProblemBoard, pieces: Stream[Piece], partsCount: Int): List[Stream[Option[ProblemBoard]]] = {

    //println(s"partsCount=$partsCount")

    val fieldsCount = board.width * board.height
    val onePartFieldsCount: Int = fieldsCount / partsCount

    val partFieldsCount = new Array[Int](partsCount)
    for (i ← 0 until partsCount) partFieldsCount(i) = onePartFieldsCount

    val toAddToLastPart = (fieldsCount - onePartFieldsCount * partsCount)

    partFieldsCount(partsCount - 1) = partFieldsCount(partsCount - 1) + toAddToLastPart

    //partFieldsCount.foreach(part ⇒ println(s"in part $part"))

    def getPositionFromFieldNumber(fieldNumber: Int): (Int, Int) = {
      val y: Int = fieldNumber / board.height
      val x: Int = fieldNumber % board.height
      (x, y)
    }

    val list = ListBuffer[Stream[Option[ProblemBoard]]]()

    var sum = 0
    for (part ← partFieldsCount) {

      val start = sum
      //val end = sum + part - 1

      val s = getPositionFromFieldNumber(start)
      //val e = getPositionFromFieldNumber(end)

      //println(s"part = $part, start=$start, s=$s")

      list += generateBoardsStream(board, s._1, s._2, part, pieces)

      sum += part
    }

    list.toList
  }

  //def generateBoardsStream(board: ProblemBoard, pieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

  //println(s"0: pieces=${pieces.mkString(",")}")

  //  generateBoardsStream(board, 0, 0, pieces)
  //}

  def generateBoardsStream(board: ProblemBoard, startX: Int, startY: Int, count: Int, pieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

    //println(s"A: startX=$startX, startY=$startY pieces=${pieces.mkString(",")}")

    if (board.getSafeFieldsCount >= pieces.length) {

      pieces match {

        case piece #:: restOfPieces ⇒ {

          //Stream[Option[ProblemBoard]](None) ++: {
          None #:: {

            val positionedPieceStream = {
              val positions = Position2.generatePositionsStream(startX, startY, board.width, board.height, count)
              PositionedPiece.generatePositionedPiecesStream(piece, positions)
            }

            generateBoards(board, positionedPieceStream, restOfPieces)
          }
        }

        case _ ⇒ {
          //Some(board) #:: Stream.empty
          //Stream[Option[ProblemBoard]](Some(board))
          Stream.empty
        }
      }
    } else {
      Stream.empty
    }
  }

  def generateBoards(board: ProblemBoard, positionedPieceStream: Stream[PositionedPiece], restOfPieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

    //println(s"B: restOfPieces=${restOfPieces.mkString(",")} positionedPieceStream=${positionedPieceStream.mkString(",")}")

    positionedPieceStream match {

      case positionedPiece #:: restOfPositionedPieceStream ⇒ {

        //println(s"B: tooked from the stream positionedPiece = ${positionedPiece}, restOfPositionedPieceStream = ${restOfPositionedPieceStream.mkString(", ")}")

        board.put(positionedPiece.x, positionedPiece.y, positionedPiece.piece) match {

          case Left(e) ⇒ {
            if (restOfPositionedPieceStream.isEmpty) {
              //println(s"B: could not put ($e), and there is nothing to go left, returning empty")
              //None #:: Stream.empty
              Stream.empty
            } else {
              //println(s"B: could not put ($e), will go left")
              //val goLeft =
              None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
              //println("B: could not put, returning left")
              //goLeft
            }

            //goLeft

            //Stream[Option[ProblemBoard]](None) ++: goLeft

            //Stream[Option[ProblemBoard]](None)
          }

          case Right(nextBoard) ⇒ {

            /*
            if (restOfPieces.isEmpty) {
              //println("A: --------- board is ready!! -------------")
              //println(as.chess.problem.drawer.AsciiDrawer.draw(nextBoard))
              //Thread.sleep(1000)

              if (restOfPositionedPieceStream.isEmpty) {
                //Stream[Option[ProblemBoard]](Some(nextBoard))
                Some(nextBoard) #:: Stream.empty
              } else {
                Some(nextBoard) #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
              }

            } else {
              */
            //println(as.chess.problem.drawer.AsciiDrawer.draw(nextBoard))
            //Thread.sleep(200)

            //println("B: preparing ...")

            if (restOfPieces.isEmpty) {

              if (restOfPositionedPieceStream.isEmpty) {
                //println("B: will go only down")
                //None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces)

                //println("B: will go left only")
                Some(nextBoard) #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
              } else {
                //println("B: will go down and left")
                //None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces) //++: generateBoards(board, restOfPositionedPieceStream, restOfPieces)

                //println("B: will go left only")
                Some(nextBoard) #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
              }
            } else {

              var positionOfNextPiece: (Int, Int) = (0, 0)

              if (restOfPieces(0) == positionedPiece.piece) {
                positionOfNextPiece = Position2.getNextPosition(positionedPiece.x, positionedPiece.y, board.width, board.height) match {
                  case Some(np) ⇒ np
                  case None     ⇒ positionOfNextPiece
                }
                //startX = positionedPiece.x + 1
                //startY = positionedPiece.y
              }

              if (restOfPositionedPieceStream.isEmpty) {
                //println("B: will go down only")

                //None #:: Stream.empty
                None #:: generateBoardsStream(nextBoard, positionOfNextPiece._1, positionOfNextPiece._2, board.width * board.height, restOfPieces)
              } else {

                //println("B: will go down and left")

                //None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces) ++: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
                None #:: generateBoardsStream(nextBoard, positionOfNextPiece._1, positionOfNextPiece._2, board.width * board.height, restOfPieces) ++: None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
              }
            }

            //val goDown = if (restOfPieces.isEmpty) None #:: Stream.empty else None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces)
            //val goLeft = if (restOfPositionedPieceStream.isEmpty) None #:: Stream.empty else None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)

            //++: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
            //None #:: goDown ++: goLeft
            //}

            //None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, mbs, piecesOnBoard) ++: generateUniqueBoardsStream(nextBoard, 0, 0, restOfPieces, mbs, updatedPiecesOnBoard)
          }
        }
      }

      case _ ⇒ {
        //println("B: no more positioned pieces to process, returning empty")
        Stream.empty
      }
    }
  }
}