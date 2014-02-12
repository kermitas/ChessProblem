package as.chessproblem.application.accumulator

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.ama.startup.InitializationResult
import com.typesafe.config.Config

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

    case Messages.BoardsAcceptiationFinished ⇒ {
      broadcaster ! new Messages.AccumulatedAcceptedBoards(new scala.collection.mutable.ListBuffer[ProblemBoard])
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}