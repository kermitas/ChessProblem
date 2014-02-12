package as.chessproblem.application.publisher

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class AcceptedBoardsPublisherClassifier extends Classifier {
  override def map(message: Any, sender: ActorRef) = if (message.isInstanceOf[Messages.AcceptedBoard] || message.isInstanceOf[Messages.AccumulatedAcceptedBoards] || message.isInstanceOf[Messages.ProblemSettings]) Some(message) else None
}