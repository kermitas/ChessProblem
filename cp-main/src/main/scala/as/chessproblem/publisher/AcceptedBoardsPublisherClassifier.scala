package as.chessproblem.publisher

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class AcceptedBoardsPublisherClassifier extends Classifier {

  override def map(message: Any, sender: ActorRef) = message match {
    case ab: Messages.AcceptedBoard              ⇒ Some(ab)
    case aab: Messages.AccumulatedAcceptedBoards ⇒ Some(aab)
    case ps: Messages.ProblemSettings            ⇒ Some(ps)
    case _                                       ⇒ None
  }

}