package as.chessproblem.application.problem

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.ama.addon.lifecycle.LifecycleListener

class SmartGameEvaluatorClassifier extends Classifier {

  //override def map(message: Any, sender: ActorRef) = if (message.isInstanceOf[Messages.ProblemSettings] || message.isInstanceOf[LifecycleListener.ShutdownSystem]) Some(message) else None

  override def map(message: Any, sender: ActorRef) = message match {
    case ps: Messages.ProblemSettings         ⇒ Some(ps)
    case ss: LifecycleListener.ShutdownSystem ⇒ Some(ss)
    case _                                    ⇒ None
  }
}