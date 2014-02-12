package as.chessproblem.accumulator

import akka.actor.{ ActorRef, ActorLogging, Actor }
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.ama.startup._
import com.typesafe.config.Config

class AcceptedBoardsAccumulator(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  protected val acceptedBoards = new scala.collection.mutable.ListBuffer[ProblemBoard]

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

    case Messages.BoardsAcceptiationFinished ⇒ {
      broadcaster ! new Messages.AccumulatedAcceptedBoards(acceptedBoards)
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}