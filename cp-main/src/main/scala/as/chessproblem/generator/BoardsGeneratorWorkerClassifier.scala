package as.chessproblem.generator

import akka.actor.ActorRef
import as.akka.broadcaster.Classifier
import as.ama.addon.lifecycle.LifecycleListener

class BoardsGeneratorWorkerClassifier extends Classifier {

  override def map(message: Any, sender: ActorRef) = message match {
    case ss: LifecycleListener.ShutdownSystem ⇒ Some(ss)
    case _                                    ⇒ None
  }
}