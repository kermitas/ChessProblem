package as.chessproblem.controller

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.ama.addon.lifecycle._
import as.ama.startup._
import com.typesafe.config.Config
import as.ama.addon.inputstream.InputStreamListenerCallbackImpl

object ProcessController {
  final val shutdownOnAllBoardsPublishedConfigKey = "shutdownOnAllBoardsPublished"
  final val shutdownOnAllBoardsSavedConfigKey = "shutdownOnAllBoardsSaved"
}

/**
 * Will shutdown system on AcceptedBoardsWerePublishedToFile or AcceptedBoardsWerePublishedToLog or InputStreamText.
 */
class ProcessController(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  import ProcessController._

  protected var shutdownOnAllBoardsPublished: Boolean = false
  protected var shutdownOnAllBoardsSaved: Boolean = false

  override def preStart() {
    try {
      shutdownOnAllBoardsPublished = config.getBoolean(shutdownOnAllBoardsPublishedConfigKey)
      shutdownOnAllBoardsSaved = config.getBoolean(shutdownOnAllBoardsSavedConfigKey)

      broadcaster ! new Broadcaster.Register(self, new ProcessControllerClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing shutdown starter (on work done).", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.AcceptedBoardsWerePublishedToFile ⇒ if (shutdownOnAllBoardsSaved) shutdownOnWorkDone

    case Messages.AcceptedBoardsWerePublishedToLog  ⇒ if (shutdownOnAllBoardsPublished) shutdownOnWorkDone

    case InputStreamListenerCallbackImpl.InputStreamText(inputText) ⇒ {
      log.info(s"Input text ('$inputText') detected, finishing...")
      broadcaster ! new LifecycleListener.ShutdownSystem(Right(s"Detected key press, finishing!"))
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }

  protected def shutdownOnWorkDone {
    broadcaster ! new LifecycleListener.ShutdownSystem(Right(s"Work done, bye!"))
    context.stop(self)
  }
}