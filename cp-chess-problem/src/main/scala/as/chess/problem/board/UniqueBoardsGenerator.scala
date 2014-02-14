package as.chess.problem.board

import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.board.path.BlacklistedPaths
import as.chess.problem.geom.Position

object UniqueBoardsGenerator {

  def generateUniqueBoardsStream(board: ProblemBoard, pieces: Stream[Piece], workMode: BlacklistedPaths.WorkMode): Stream[Option[ProblemBoard]] = {
    generateUniqueBoardsStream(board, 0, 0, pieces, BlacklistedPaths.createPathsBlacklister(board.width, board.height, workMode), List[PositionedPiece]())
  }

  def generateUniqueBoardsStream(board: ProblemBoard, startX: Int, startY: Int, pieces: Stream[Piece], bp: BlacklistedPaths, path: List[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    if (board.getSafeFieldsCount >= pieces.length) {

      pieces match {

        case piece #:: restOfPieces ⇒ {
          None #:: {

            val positionedPieceStream = {
              val positions = Position.generatePositionsStream(startX, startY, board.width, board.height)

              PositionedPiece.generatePositionedPiecesStream(piece, positions)
            }

            generateBoards(board, positionedPieceStream, restOfPieces, bp, path)
          }
        }

        case _ ⇒ Some(board) #:: Stream.empty
      }
    } else {
      Stream.empty
    }
  }

  def generateBoards(board: ProblemBoard, positionedPieceStream: Stream[PositionedPiece], restOfPieces: Stream[Piece], bp: BlacklistedPaths, path: List[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    positionedPieceStream match {

      case positionedPiece #:: restOfPositionedPieceStream ⇒ {

        val nextPath = path :+ positionedPiece

        if (!bp.isBlacklisted(nextPath)) {
          board.put(positionedPiece.x, positionedPiece.y, positionedPiece.piece) match {

            case Left(e) ⇒ None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, path)

            case Right(nextBoard) ⇒ {

              bp.blacklist(nextPath)

              var startX = 0
              var startY = 0

              /*
              if (!restOfPositionedPieceStream.isEmpty && restOfPositionedPieceStream(0).piece == positionedPiece.piece) {
                startX = positionedPiece.x
                startY = positionedPiece.y
              }
              */

              None #:: generateUniqueBoardsStream(nextBoard, startX, startY, restOfPieces, bp, nextPath) ++: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, path)
            }
          }
        } else {
          None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, path)
        }
      }

      case _ ⇒ Stream.empty
    }
  }
}
