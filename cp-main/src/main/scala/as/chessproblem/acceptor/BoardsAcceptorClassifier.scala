package as.chessproblem.acceptor

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class BoardsAcceptorClassifier extends Classifier {
  override def map(message: Any, sender: ActorRef) = if (message.isInstanceOf[Messages.GeneratedBoard] || message == Messages.AllBoardsWereGenerated) Some(message) else None
}