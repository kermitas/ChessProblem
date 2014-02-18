package as.chessproblem.accumulator

import scala.collection.mutable.ListBuffer
import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.Board
import as.ama.startup.InitializationResult
import com.typesafe.config.Config

/**
 * Does not do any accumulation. Simply sends AccumulatedAcceptedBoards with empty list after receiving BoardsAcceptationFinished.
 */
class AcceptedBoardsFakeAccumulator(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new AcceptedBoardsAccumulatorClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'accepted boards fake accumulator'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.AcceptedBoard(board) ⇒

    case Messages.BoardsAcceptationFinished ⇒ {
      broadcaster ! new Messages.AccumulatedAcceptedBoards(new ListBuffer[Board])
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}