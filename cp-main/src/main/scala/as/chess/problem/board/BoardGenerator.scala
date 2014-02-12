package as.chess.problem.board

import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.geom.Position
import as.chess.problem.board.path.BlacklistedPaths

object BoardGenerator {

  def generateBoardsStream(board: ProblemBoard, pieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {
    generateBoardsStream(board, pieces, new BlacklistedPaths(board.width, board.height), List[PositionedPiece]())
  }

  def generateBoardsStream(board: ProblemBoard, pieces: Stream[Piece], bp: BlacklistedPaths, path: List[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    //println("generateBoards1: HELLO, entering with board = " + as.chess.problem.drawer.AsciiDrawer.draw(board) + " pieces = " + pieces.mkString(","))

    if (board.getSafeFieldsCount >= pieces.length) {

      pieces match {

        case piece #:: restOfPieces ⇒ {
          val positions = Position.generatePositionsStream(0, 0, board.width, board.height)
          val positionedPieceStream = PositionedPiece.generatePositionedPiecesStream(piece, positions)

          //println("generateBoards1: board before jumping into generateBoards2 = " + as.chess.problem.drawer.AsciiDrawer.draw(board))

          //None #:: generateBoards2(board, positionedPieceStream, restOfPieces, bp, path)
          None #:: generateBoards2(board, positionedPieceStream, restOfPieces, bp, path) //++ generateBoards1(board, restOfPieces, bp, path)
        }

        case _ ⇒ {
          // TODO add board rotations

          //println("generateBoards1: no more pieces, resulting board = " + as.chess.problem.drawer.AsciiDrawer.draw(board))

          Some(board) #:: Stream.empty
          //transofrmGeneratedBoard(board) ++ Stream.empty
        }
      }
    } else {

      //println("generateBoards1: not enough empty safe fields, returning empty stream")

      Stream.empty
    }
  }

  def generateBoards2(board: ProblemBoard, positionedPieceStream: Stream[PositionedPiece], restOfPieces: Stream[Piece], bp: BlacklistedPaths, path: List[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    //println("generateBoards2: HeLlO, entering with board = " + as.chess.problem.drawer.AsciiDrawer.draw(board) + "piece to put = " + positionedPieceStream + " restOfPieces = " + restOfPieces.mkString(","))

    positionedPieceStream match {

      case positionedPiece #:: restOfPositionedPieceStream ⇒ {

        val nextPath = path :+ positionedPiece

        if (!bp.isBlacklisted(nextPath)) {
          board.put(positionedPiece.x, positionedPiece.y, positionedPiece.piece) match {

            case Left(e) ⇒ {
              //println(s"generateBoards2: could not put piece $positionedPiece on board, returning None and rest of stream")
              None #:: generateBoards2(board, restOfPositionedPieceStream, restOfPieces, bp, path)
            }

            case Right(nextBoard) ⇒ {

              //println("generateBoards2: nextBoard = " + as.chess.problem.drawer.AsciiDrawer.draw(nextBoard))
              //println(s"generateBoards2: blacklist $positionedPiece under path /${path.mkString("/")}")

              //println(s"generateBoards2: will blacklist (last one was added) /${nextPath.mkString("/")}")

              bp.blacklist(nextPath)

              //println(s"generateBoards2: path after adding $positionedPiece = /${nextPath.mkString("/")}")
              //generateBoards1(nextBoard, restOfPieces, bp, nextPath) //++ generateBoards2(board, restOfPositionedPieceStream, restOfPieces, bp, path)
              //generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, path) ++ generateBoards(nextBoard, restOfPieces, bp, nextPath)

              //var startX = 0
              //var startY = 0

              /*
              if (!restOfPositionedPieceStream.isEmpty) {
                if (restOfPositionedPieceStream(0).piece == positionedPiece.piece) {
                  startX = positionedPiece.x+1
                  startY = positionedPiece.y

                  println(s"generateBoards2: modify startX and startY from (0,0) to ($startX,$startY)")
                }
              }*/

              None #:: generateBoardsStream(nextBoard, restOfPieces, bp, nextPath) ++: generateBoards2(board, restOfPositionedPieceStream, restOfPieces, bp, path)
              //None #:: generateBoards2(board, restOfPositionedPieceStream, restOfPieces, bp, path) ++ generateBoards1(nextBoard, restOfPieces, bp, nextPath, startX, startY)
            }
          }
        } else {
          //println(s"generateBoards2: sorry ${positionedPiece.x},${positionedPiece.y} is not valid under path /${path.mkString("/")}!!!!!")
          None #:: generateBoards2(board, restOfPositionedPieceStream, restOfPieces, bp, path)
        }
      }

      case _ ⇒ {

        //println("generateBoards2: no more positioned pieces to put on board, returning empty stream")
        Stream.empty
      }
    }
  }
}

