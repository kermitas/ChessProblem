package as.chessproblem.acceptor

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.{ Board ⇒ ProblemBoard }
import as.ama.startup._
import com.typesafe.config.Config

class UniqueBoardsAcceptor(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  protected var collectedUniqueResults = scala.collection.parallel.mutable.ParArray[ProblemBoard]()

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new BoardsAcceptorClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'unique boards acceptor'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.GeneratedBoard(board) ⇒ {
      collectedUniqueResults.find(_.equals(board)).getOrElse {
        collectedUniqueResults = collectedUniqueResults :+ board
        broadcaster ! new Messages.AcceptedBoard(board)
      }
    }

    case Messages.AllBoardsWereGenerated ⇒ {
      broadcaster ! Messages.BoardsAcceptiationFinished
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}