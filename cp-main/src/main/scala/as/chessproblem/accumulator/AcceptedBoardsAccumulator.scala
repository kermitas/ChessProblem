package as.chessproblem.accumulator

import scala.collection.mutable.ListBuffer
import akka.actor.{ ActorRef, ActorLogging, Actor }
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.Board
import as.ama.startup._
import com.typesafe.config.Config

/**
 * Accumulates boards and publish them in AccumulatedAcceptedBoards message after receiving BoardsAcceptationFinished.
 */
class AcceptedBoardsAccumulator(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  protected val acceptedBoards = new ListBuffer[Board]

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new AcceptedBoardsAccumulatorClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'accepted boards accumulator'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.AcceptedBoard(board) ⇒ acceptedBoards += board

    case Messages.BoardsAcceptationFinished ⇒ {
      broadcaster ! new Messages.AccumulatedAcceptedBoards(acceptedBoards)
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}