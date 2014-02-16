package as.chess.problem.board

import scala.collection.immutable.TreeSet
import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.piece.set.MutableBlacklistedSets
import as.chess.problem.geom.Position
import as.chess.problem.piece.set.DistanceBasedPositionedPieceInSetOrdering

object UniqueBoardsGenerator {

  def generateUniqueBoardsStream(board: ProblemBoard, pieces: Stream[Piece]): Stream[Option[ProblemBoard]] = {

    val treeSetBuilder: scala.collection.generic.CanBuildFrom[TreeSet[PositionedPiece], PositionedPiece, TreeSet[PositionedPiece]] = TreeSet.newCanBuildFrom[PositionedPiece](new DistanceBasedPositionedPieceInSetOrdering(board.height))

    val bl = new MutableBlacklistedSets(board.width, board.height, treeSetBuilder)
    val piecesOnBoard = TreeSet[PositionedPiece]()(new DistanceBasedPositionedPieceInSetOrdering(board.height))
    generateUniqueBoardsStream(board, 0, 0, pieces, bl, piecesOnBoard)
  }

  def generateUniqueBoardsStream(board: ProblemBoard, startX: Int, startY: Int, pieces: Stream[Piece], bp: MutableBlacklistedSets, piecesOnBoard: TreeSet[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    if (board.getSafeFieldsCount >= pieces.length) {

      pieces match {

        case piece #:: restOfPieces ⇒ {

          //println(s"A: taking first piece $piece and trying to put it, piecesOnBoard=${piecesOnBoard.mkString(", ")}, restOfPieces=${restOfPieces.mkString(", ")}")

          None #:: {

            val positionedPieceStream = {
              val positions = Position.generatePositionsStream(startX, startY, board.width, board.height)
              PositionedPiece.generatePositionedPiecesStream(piece, positions)
            }

            generateBoards(board, positionedPieceStream, restOfPieces, bp, piecesOnBoard)
          }
        }

        case _ ⇒ {

          //bp.blacklist(piecesOnBoard)

          //bp.printSets()
          //println("===== Board is ready!!! ======")
          //println(as.chess.problem.drawer.AsciiDrawer.draw(board))
          //Thread.sleep(3000)

          Some(board) #:: Stream.empty
        }
      }
    } else {
      Stream.empty
    }
  }

  def generateBoards(board: ProblemBoard, positionedPieceStream: Stream[PositionedPiece], restOfPieces: Stream[Piece], bp: MutableBlacklistedSets, piecesOnBoard: TreeSet[PositionedPiece]): Stream[Option[ProblemBoard]] = {

    positionedPieceStream match {

      case positionedPiece #:: restOfPositionedPieceStream ⇒ {

        //println(s"B: taking first positionedPiece $positionedPiece and trying to put it, piecesOnBoard=${piecesOnBoard.mkString(", ")}")

        val updatedPiecesOnBoard = piecesOnBoard + positionedPiece

        //var updatedPiecesOnBoard = TreeSet[PositionedPiece](positionedPiece)(new DistanceBasedPositionedPieceOrdering)
        //updatedPiecesOnBoard = updatedPiecesOnBoard ++ piecesOnBoard

        //println(s"!! sorted SET ${updatedPiecesOnBoard.mkString(", ")}")

        //updatedPiecesOnBoard = updatedPiecesOnBoard + positionedPiece

        if (updatedPiecesOnBoard.size == piecesOnBoard.size) {

          //println(s"$positionedPiece is currently on board (collision), continuing...")

          None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, piecesOnBoard)

        } else {
          if (!bp.isBlacklisted(updatedPiecesOnBoard)) {

            //println(s"set ${updatedPiecesOnBoard.mkString(", ")} not blacklisted ")

            //bp.blacklist(updatedPiecesOnBoard)

            //bp.blacklist(piecesOnBoard, positionedPiece)

            board.put(positionedPiece.x, positionedPiece.y, positionedPiece.piece) match {

              case Left(e) ⇒ {
                //println(s"could not put $positionedPiece on board :/ ")
                //bp.blacklist(piecesOnBoard, positionedPiece)
                None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, piecesOnBoard)
              }

              case Right(nextBoard) ⇒ {

                //println(s"success, $positionedPiece IS on board ::)) ")

                bp.blacklist(piecesOnBoard, positionedPiece)

                //println(as.chess.problem.drawer.AsciiDrawer.draw(nextBoard))
                //Thread.sleep(300)

                /*
                var startX = 0
                var startY = 0

                if (!restOfPositionedPieceStream.isEmpty) {
                  val nextPiece = restOfPositionedPieceStream(0).piece

                  println(s"Next piece = $nextPiece, pieces currently on board: ${updatedPiecesOnBoard.mkString(", ")}")
                  val filtered = updatedPiecesOnBoard.filter(pp ⇒ pp.piece == nextPiece)

                  println(s"pieces currently on board filtered to only this in type of next piece : ${filtered.mkString(", ")}")

                  filtered.lastOption match {
                    case Some(lastPositionedPieceSameAsNextPiece) ⇒ {

                      println(s"last one from filtered list: $lastPositionedPieceSameAsNextPiece")

                      startX = lastPositionedPieceSameAsNextPiece.x
                      startY = lastPositionedPieceSameAsNextPiece.y
                    }
                    case None ⇒
                  }
                }
                */

                /*
                if (!restOfPositionedPieceStream.isEmpty && restOfPositionedPieceStream(0).piece == positionedPiece.piece) {
                  startX = positionedPiece.x + 1
                  startY = positionedPiece.y
                }*/

                None #:: generateUniqueBoardsStream(nextBoard, 0, 0, restOfPieces, bp, updatedPiecesOnBoard) ++: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, piecesOnBoard)
                //None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, piecesOnBoard) ++: generateUniqueBoardsStream(nextBoard, startX, startY, restOfPieces, bp, updatedPiecesOnBoard)
              }
            }
          } else {

            //println(s"set ${updatedPiecesOnBoard.mkString(", ")} IS blacklisted ")

            None #:: generateBoards(board, restOfPositionedPieceStream, restOfPieces, bp, piecesOnBoard)
            //None #:: Stream.empty

            //None #:: Stream.empty
            //Stream.empty
          }
        }
      }

      case _ ⇒ Stream.empty
    }
  }
}

/*
import as.chess.problem.piece.{ PositionedPiece, Piece }
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.board.path.BlacklistedPaths
import as.chess.problem.geom.Position

object UniqueBoardsGenerator {

  def generateUniqueBoardsStream(board: ProblemBoard, pieces: Stream[Piece], workMode: BlacklistedPaths.WorkStrategy): Stream[Option[ProblemBoard]] = {
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

 */ 