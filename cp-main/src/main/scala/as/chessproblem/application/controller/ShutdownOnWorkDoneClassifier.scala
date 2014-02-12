package as.chessproblem.application.controller

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class ShutdownOnWorkDoneClassifier extends Classifier {
  override def map(message: Any, sender: ActorRef) = if (message == Messages.AcceptedBoardsWerePublishedToFile || message == Messages.AcceptedBoardsWerePublishedToLog) Some(message) else None
}