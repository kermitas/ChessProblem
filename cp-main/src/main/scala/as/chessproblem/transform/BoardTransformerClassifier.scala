package as.chessproblem.transform

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class BoardTransformerClassifier extends Classifier {
  override def map(message: Any, sender: ActorRef) = if (message.isInstanceOf[Messages.GeneratedUniqueBoard] || message == Messages.AllUniqueBoardsWereGenerated) Some(message) else None
}