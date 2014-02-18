package as.chessproblem.generator

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.ama.startup._
import com.typesafe.config.Config
import as.ama.addon.lifecycle.LifecycleListener
import as.chess.problem.board.{ BoardsGenerator ⇒ BoardsStreamGenerator }

object BoardsGenerator {
  final val parallelProcessingFactorConfigKey = "parallelProcessingFactor"
}

/**
 * After receiving ProblemSettings will prepare board streams and start workers (that will pull those streams)
 */
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

    case Messages.ProblemSettings(board, pieces) ⇒ {

      log.info(s"Will start $parallelProcessingFactor parallel actors that will pull boards from $parallelProcessingFactor streams (each actor will have its own stream).")

      var i = 0
      for (stream ← BoardsStreamGenerator.generateBoardsStream(board, pieces, parallelProcessingFactor)) {
        val worker = context.actorOf(BoardsGeneratorWorker.props(broadcaster), name = classOf[BoardsGeneratorWorker].getSimpleName + "-" + i)
        i += 1
        context.watch(worker)
        worker ! stream
      }
    }

    /* If all our children died that means that all boards were generated.*/
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

