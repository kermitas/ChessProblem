package as.chessproblem.application.publisher.log

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.drawer._
import as.ama.startup._
import com.typesafe.config.Config
import as.chessproblem.application.publisher.AcceptedBoardsPublisherClassifier

class AcceptedBoardsLogPublisher(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  protected var acceptedBoardsLogPublisherConfig: AcceptedBoardsLogPublisherConfig = _
  protected var acceptedResultsCount = 0
  protected var startTime: Long = 0

  override def preStart() {
    try {
      acceptedBoardsLogPublisherConfig = AcceptedBoardsLogPublisherConfig(config)
      broadcaster ! new Broadcaster.Register(self, new AcceptedBoardsPublisherClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'accepted boards printer'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.AcceptedBoard(board) ⇒ {
      acceptedResultsCount += 1

      if (acceptedBoardsLogPublisherConfig.printProducedBoardNumberModuloFactor > 0 && acceptedResultsCount % acceptedBoardsLogPublisherConfig.printProducedBoardNumberModuloFactor == 0) {
        log.info(s"Boards count: $acceptedResultsCount")
      }

      if (acceptedBoardsLogPublisherConfig.drawBoardsWhileProduced) {
        log.info("Board #" + acceptedResultsCount)
        log.info(AsciiDrawer.draw(board, acceptedBoardsLogPublisherConfig.asciiDefinitions))
      }
    }

    case Messages.AccumulatedAcceptedBoards(results) ⇒ {

      if (startTime > 0) {
        val timeInS = (System.currentTimeMillis - startTime) / 1000
        log.info(s"Chess problem results count ${acceptedResultsCount} in ${timeInS} seconds.")
      } else
        log.info(s"Chess problem results count ${acceptedResultsCount}")

      if (acceptedBoardsLogPublisherConfig.drawBoardsAtTheEnd) {
        var i = 0
        for (board ← results) {
          log.info(s"Board number $i of $acceptedResultsCount (still to print ${acceptedResultsCount - i})")
          log.info(AsciiDrawer.draw(board, acceptedBoardsLogPublisherConfig.asciiDefinitions))
          i += 1
        }
      }

      broadcaster ! Messages.AcceptedBoardsWerePublishedToLog
      context.stop(self)
    }

    case ps: Messages.ProblemSettings ⇒ startTime = System.currentTimeMillis

    case message                      ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }
}