package as.chessproblem

import as.chess.problem.board.Board
import as.chess.problem.piece.Piece

import scala.collection.GenSeq

object Messages {
  case class ProblemSettings(board: Board, pieces: Stream[Piece]) extends Serializable

  case class GeneratedUniqueBoard(board: Board) extends Serializable
  case object AllUniqueBoardsWereGenerated extends Serializable

  case class GeneratedBoard(board: Board) extends Serializable
  case object AllBoardsWereGenerated extends Serializable

  case class AcceptedBoard(board: Board) extends Serializable
  case object BoardsAcceptationFinished extends Serializable

  case class AccumulatedAcceptedBoards(results: GenSeq[Board]) extends Serializable

  case object AcceptedBoardsWerePublishedToLog extends Serializable
  case object AcceptedBoardsWerePublishedToFile extends Serializable
}
