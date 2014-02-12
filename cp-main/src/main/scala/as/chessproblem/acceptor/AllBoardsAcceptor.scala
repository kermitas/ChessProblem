package as.chessproblem.acceptor

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.ama.startup._
import com.typesafe.config.Config

class AllBoardsAcceptor(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new BoardsAcceptorClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'all boards acceptor'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.GeneratedBoard(board) ⇒ broadcaster ! new Messages.AcceptedBoard(board)

    case Messages.AllBoardsWereGenerated ⇒ {
      broadcaster ! Messages.BoardsAcceptiationFinished
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}