package as.chessproblem.generator

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.Board
import as.ama.startup._
import com.typesafe.config.Config
import as.ama.addon.lifecycle.LifecycleListener

object BoardsGenerator {
  final val parallelProcessingFactorConfigKey = "parallelProcessingFactor"
}

class BoardsGenerator(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  import BoardsGenerator._

  protected var parallelProcessingFactor: Int = 1

  override def preStart() {
    try {

      parallelProcessingFactor = config.getInt(parallelProcessingFactorConfigKey)
      if (parallelProcessingFactor <= 0) parallelProcessingFactor = Runtime.getRuntime.availableProcessors

      broadcaster ! new Broadcaster.Register(self, new BoardsGeneratorClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing boards generator.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case ps: Messages.ProblemSettings ⇒ {

      log.debug(s"Will start $parallelProcessingFactor parallel processes that will pull boards from streams")

      for (i ← 0 until parallelProcessingFactor) {
        val worker = context.actorOf(BoardsGeneratorWorker.props(broadcaster, i, parallelProcessingFactor), name = classOf[BoardsGeneratorWorker].getSimpleName + "-" + i)
        context.watch(worker)
        worker ! ps
      }
    }

    case Terminated(diedActor) ⇒ {
      if (context.children.isEmpty) {
        broadcaster ! Messages.AllBoardsWereGenerated
        context.stop(self)
      }
    }

    case ss: LifecycleListener.ShutdownSystem ⇒ context.stop(self)

    case message                              ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}

