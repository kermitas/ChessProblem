package as.chessproblem

import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.chess.problem.piece.Piece

import scala.collection.GenSeq

object Messages {
  case class ProblemSettings(board: ProblemBoard, pieces: Stream[Piece]) extends Serializable

  case class GeneratedUniqueBoard(board: ProblemBoard) extends Serializable
  case object AllUniqueBoardsWereGenerated extends Serializable

  case class GeneratedBoard(board: ProblemBoard) extends Serializable
  case object AllBoardsWereGenerated extends Serializable

  case class AcceptedBoard(board: ProblemBoard) extends Serializable
  case object BoardsAcceptiationFinished extends Serializable

  case class AccumulatedAcceptedBoards(results: GenSeq[ProblemBoard]) extends Serializable

  case object AcceptedBoardsWerePublishedToLog extends Serializable
  case object AcceptedBoardsWerePublishedToFile extends Serializable
}
