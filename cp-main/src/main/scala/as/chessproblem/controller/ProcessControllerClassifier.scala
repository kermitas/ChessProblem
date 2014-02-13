package as.chessproblem.controller

import as.akka.broadcaster.Classifier
import akka.actor.ActorRef
import as.chessproblem.Messages
import as.ama.addon.inputstream.InputStreamListenerCallbackImpl

class ProcessControllerClassifier extends Classifier {

  override def map(message: Any, sender: ActorRef) = message match {
    case Messages.AcceptedBoardsWerePublishedToFile ⇒ Some(message)
    case Messages.AcceptedBoardsWerePublishedToLog ⇒ Some(message)
    case ist: InputStreamListenerCallbackImpl.InputStreamText ⇒ Some(ist)
    case _ ⇒ None
  }

}