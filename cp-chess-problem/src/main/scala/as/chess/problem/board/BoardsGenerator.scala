package as.chess.problem.board

import scala.collection.immutable.TreeSet
import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.piece.set2.MutableBlacklistedSets3
import as.chess.problem.geom.Position
import as.chess.problem.piece.set.DistanceBasedPositionedPieceInSetOrdering

object BoardsGenerator {

  def generateBoardsStream(board: ProblemBoard, pieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

    //val s1 = Stream[Option[String]]({ println("s1"); None })
    //val s2 = Stream[Option[String]]({ println("s2"); Some("aa") })

    //val s3 = Stream[Option[String]]({ println("s1"); None }) + { println("s2"); Some("aa"); "bb" }
    //println(s"0: pieces=${pieces.mkString(",")}")

    generateBoardsStream(board, 0, 0, pieces)
  }

  def generateBoardsStream(board: ProblemBoard, startX: Int, startY: Int, pieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

    println(s"A: startX=$startX, startY=$startY pieces=${pieces.mkString(",")}")

    if (board.getSafeFieldsCount >= pieces.length) {

      pieces match {

        case piece #:: restOfPieces ⇒ {

          Stream[Option[ProblemBoard]](None) ++: {

            val positionedPieceStream = {
              val positions = Position.generatePositionsStream(startX, startY, board.width, board.height)
              PositionedPiece.generatePositionedPiecesStream(piece, positions)
            }

            generateBoards(board, positionedPieceStream, restOfPieces)
          }
        }

        case _ ⇒ {
          //Some(board) #:: Stream.empty
          Stream[Option[ProblemBoard]](Some(board))
        }
      }
    } else {
      Stream.empty
    }
  }

  def generateBoards(board: ProblemBoard, positionedPieceStream: Stream[PositionedPiece], restOfPieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

    println(s"B: restOfPieces=${restOfPieces.mkString(",")} positionedPieceStream=${positionedPieceStream.mkString(",")}")

    positionedPieceStream match {

      case positionedPiece #:: restOfPositionedPieceStream ⇒ {

        println(s"B: tooked from the stream positionedPiece = ${positionedPiece}, restOfPositionedPieceStream = ${restOfPositionedPieceStream.mkString(", ")}")

        board.put(positionedPiece.x, positionedPiece.y, positionedPiece.piece) match {

          case Left(e) ⇒ {
            if (restOfPositionedPieceStream.isEmpty) {
              println(s"B: could not put ($e), and there is nothing to go left, returning empty")
              None #:: Stream.empty
            } else {
              println(s"B: could not put ($e), will go left")
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

            if (restOfPieces.isEmpty) {
              println("A: --------- board is ready!! -------------")
              println(as.chess.problem.drawer.AsciiDrawer.draw(nextBoard))
              Thread.sleep(1000)

              if (restOfPositionedPieceStream.isEmpty) {
                //Stream[Option[ProblemBoard]](Some(nextBoard))
                Some(nextBoard) #:: Stream.empty
              } else {
                Some(nextBoard) #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
              }

            } else {
              println(as.chess.problem.drawer.AsciiDrawer.draw(nextBoard))
              Thread.sleep(300)

              var startX = 0
              var startY = 0

              if (!restOfPositionedPieceStream.isEmpty && restOfPositionedPieceStream(0).piece == positionedPiece.piece) {
                startX = positionedPiece.x + 1
                startY = positionedPiece.y
              }

              println("B: preparing ...")

              if (restOfPieces.isEmpty) {

                if (restOfPositionedPieceStream.isEmpty) {
                  println("B: will go only down")
                  None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces)
                } else {

                  println("B: will go down and left")
                  None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces) //++: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
                }
              } else {
                if (restOfPositionedPieceStream.isEmpty) {
                  println("B: will not go down and/or left")
                  None #:: Stream.empty
                } else {

                  println("B: will go down and left (but donw only!)")
                  //None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces) ++: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
                  None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces) ++: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
                }
              }

              //val goDown = if (restOfPieces.isEmpty) None #:: Stream.empty else None #:: generateBoardsStream(nextBoard, startX, startY, restOfPieces)
              //val goLeft = if (restOfPositionedPieceStream.isEmpty) None #:: Stream.empty else None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces)

              //++: generateBoards(board, restOfPositionedPieceStream, restOfPieces)
              //None #:: goDown ++: goLeft
            }

            //None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, mbs, piecesOnBoard) ++: generateUniqueBoardsStream(nextBoard, 0, 0, restOfPieces, mbs, updatedPiecesOnBoard)
          }
        }
      }

      case _ ⇒ {
        println("B: no more positioned pieces to process, returning empty")
        Stream.empty
      }
    }
  }
}