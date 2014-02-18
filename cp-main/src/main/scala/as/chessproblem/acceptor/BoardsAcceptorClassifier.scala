package as.chessproblem.acceptor

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class BoardsAcceptorClassifier extends Classifier {

  override def map(message: Any, sender: ActorRef) = message match {
    case gb: Messages.GeneratedBoard     ⇒ Some(gb)
    case Messages.AllBoardsWereGenerated ⇒ Some(message)
    case _                               ⇒ None
  }

}