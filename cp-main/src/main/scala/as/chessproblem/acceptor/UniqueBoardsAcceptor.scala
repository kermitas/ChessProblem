package as.chessproblem.acceptor

import scala.collection.parallel.mutable.ParArray
import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.Board
import as.ama.startup._
import com.typesafe.config.Config

/**
 * Accepts only unique boards.
 *
 * To do that it first accumulate all previously produced boards. Then if next one comes it looks if it is unique or duplicate.
 *
 * Look out! Not efficient if many boards are produced.
 */
class UniqueBoardsAcceptor(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  protected var collectedUniqueResults = ParArray[Board]()

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
      broadcaster ! Messages.BoardsAcceptationFinished
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}