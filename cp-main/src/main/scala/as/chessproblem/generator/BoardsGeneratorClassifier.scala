package as.chessproblem.generator

import akka.actor.ActorRef
import as.akka.broadcaster.Classifier
import as.chessproblem.Messages
import as.ama.addon.lifecycle.LifecycleListener

class BoardsGeneratorClassifier extends Classifier {

  override def map(message: Any, sender: ActorRef) = message match {
    case ps: Messages.ProblemSettings         ⇒ Some(ps)
    case ss: LifecycleListener.ShutdownSystem ⇒ Some(ss)
    case _                                    ⇒ None
  }
}