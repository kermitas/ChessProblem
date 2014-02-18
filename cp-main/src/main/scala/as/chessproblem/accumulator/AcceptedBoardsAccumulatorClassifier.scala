package as.chessproblem.accumulator

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class AcceptedBoardsAccumulatorClassifier extends Classifier {

  override def map(message: Any, sender: ActorRef) = message match {
    case ab: Messages.AcceptedBoard         ⇒ Some(ab)
    case Messages.BoardsAcceptationFinished ⇒ Some(message)
    case _                                  ⇒ None
  }

}