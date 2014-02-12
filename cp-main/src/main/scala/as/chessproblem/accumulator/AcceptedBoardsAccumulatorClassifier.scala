package as.chessproblem.accumulator

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class AcceptedBoardsAccumulatorClassifier extends Classifier {
  override def map(message: Any, sender: ActorRef) = if (message.isInstanceOf[Messages.AcceptedBoard] || message == Messages.BoardsAcceptiationFinished) Some(message) else None
}