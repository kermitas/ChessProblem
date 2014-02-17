package as.chess.problem.board

import scala.collection.immutable.TreeSet
import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.piece.set2.MutableBlacklistedSets
import as.chess.problem.geom.Position
import as.chess.problem.piece.set.DistanceBasedPositionedPieceInSetOrdering

object UniqueBoardsGenerator {

  def generateUniqueBoardsStream(board: ProblemBoard, pieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

    val treeSetBuilder: scala.collection.generic.CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]] = TreeSet.newCanBuildFrom[PositionedPiece](new DistanceBasedPositionedPieceInSetOrdering(board.height))
    val mbs = new MutableBlacklistedSets(board.width, board.height, treeSetBuilder)
    val piecesOnBoard = TreeSet[PositionedPiece]()(new DistanceBasedPositionedPieceInSetOrdering(board.height))

    generateUniqueBoardsStream(board, 0, 0, pieces, mbs, piecesOnBoard)
  }

  def generateUniqueBoardsStream(board: ProblemBoard, startX: Int, startY: Int, pieces: Stream[Piece], mbs: MutableBlacklistedSets, piecesOnBoard: TreeSet[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    if (board.getSafeFieldsCount >= pieces.length) {

      pieces match {

        case piece #:: restOfPieces ⇒ {

          None #:: {

            val positionedPieceStream = {
              val positions = Position.generatePositionsStream(startX, startY, board.width, board.height)
              PositionedPiece.generatePositionedPiecesStream(piece, positions)
            }

            generateBoards(board, positionedPieceStream, restOfPieces, mbs, piecesOnBoard)
          }
        }

        case _ ⇒ {
          Some(board) #:: Stream.empty
        }
      }
    } else {
      Stream.empty
    }
  }

  def generateBoards(board: ProblemBoard, positionedPieceStream: Stream[PositionedPiece], restOfPieces: Stream[Piece], mbs: MutableBlacklistedSets, piecesOnBoard: TreeSet[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    positionedPieceStream match {

      case positionedPiece #:: restOfPositionedPieceStream ⇒ {

        val updatedPiecesOnBoard = piecesOnBoard + positionedPiece

        if (updatedPiecesOnBoard.size == piecesOnBoard.size) {
          // positionedPiece was not added to piecesOnBoard set - can be caused by collision
          // for example: there is [(0,0) King] on the board and we are trying to add next one [(0,0) King]

          None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, mbs, piecesOnBoard)

        } else {
          if (!mbs.isBlacklisted(updatedPiecesOnBoard)) {

            board.put(positionedPiece.x, positionedPiece.y, positionedPiece.piece) match {

              case Left(e) ⇒ {
                None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, mbs, piecesOnBoard)
              }

              case Right(nextBoard) ⇒ {

                mbs.blacklist(piecesOnBoard, positionedPiece)

                None #:: generateUniqueBoardsStream(nextBoard, 0, 0, restOfPieces, mbs, updatedPiecesOnBoard) ++: generateBoards(board, restOfPositionedPieceStream, restOfPieces, mbs, piecesOnBoard)
                //None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, mbs, piecesOnBoard) ++: generateUniqueBoardsStream(nextBoard, 0, 0, restOfPieces, mbs, updatedPiecesOnBoard)
              }
            }
          } else {

            None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, mbs, piecesOnBoard)
          }
        }
      }

      case _ ⇒ Stream.empty
    }
  }
}