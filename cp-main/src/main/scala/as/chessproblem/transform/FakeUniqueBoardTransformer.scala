package as.chessproblem.transform

import akka.actor._
import as.akka.broadcaster.Broadcaster
import as.ama.startup.InitializationResult
import as.chessproblem.Messages
import com.typesafe.config.Config

/**
 * Does not do any rotations or mirroring. Just transparently passing boards forward.
 */
class FakeUniqueBoardTransformer(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new BoardTransformerClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'fake unique board transform'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {
    case Messages.GeneratedUniqueBoard(uniqueBoard) ⇒ broadcaster ! new Messages.GeneratedBoard(uniqueBoard)

    case Messages.AllUniqueBoardsWereGenerated ⇒ {
      broadcaster ! Messages.AllBoardsWereGenerated
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}