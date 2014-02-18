package as.chessproblem.transform

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages

class BoardTransformerClassifier extends Classifier {

  override def map(message: Any, sender: ActorRef) = message match {
    case gub: Messages.GeneratedUniqueBoard    ⇒ Some(gub)
    case Messages.AllUniqueBoardsWereGenerated ⇒ Some(message)
    case _                                     ⇒ None
  }
}