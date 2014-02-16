package as.chessproblem.publisher.file

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.drawer._
import as.ama.startup._
import com.typesafe.config.Config
import java.io._
import as.chessproblem.publisher.AcceptedBoardsPublisherClassifier

class AcceptedBoardsFilePublisher(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  protected var acceptedBoardsFilePublisherConfig: AcceptedBoardsFilePublisherConfig = _
  protected var acceptedResultsCount = 0
  protected var startTime: Long = 0
  var bufferedWriter: BufferedWriter = _

  override def preStart() {
    try {

      acceptedBoardsFilePublisherConfig = AcceptedBoardsFilePublisherConfig(config)

      bufferedWriter = {
        val fileWriter = new FileWriter(acceptedBoardsFilePublisherConfig.file, false)
        val printWriter = new PrintWriter(fileWriter)
        new BufferedWriter(printWriter, acceptedBoardsFilePublisherConfig.bufferSizeInBytes)
      }

      startTime = System.currentTimeMillis

      bufferedWriter.append(s"===== Will write chess boards below, start time ${startTime}ms. =====")
      bufferedWriter.newLine()

      broadcaster ! new Broadcaster.Register(self, new AcceptedBoardsPublisherClassifier)

      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'accepted boards file printer'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def postStop() {
    try { bufferedWriter.close } catch { case e: Exception ⇒ }
  }

  override def receive = {

    case Messages.AcceptedBoard(board) ⇒ {

      acceptedResultsCount += 1

      bufferedWriter.append(s"Board $acceptedResultsCount.")
      bufferedWriter.newLine()
      bufferedWriter.append(AsciiDrawer.draw(board, acceptedBoardsFilePublisherConfig.asciiDefinitions))
      bufferedWriter.newLine()
    }

    case Messages.AccumulatedAcceptedBoards(results) ⇒ {

      val stopTime = System.currentTimeMillis
      bufferedWriter.append(s"===== All $acceptedResultsCount chess boards were written in ${stopTime - startTime}ms, stop time ${stopTime}ms. =====")
      bufferedWriter.newLine()

      try { bufferedWriter.close } catch { case e: Exception ⇒ }

      broadcaster ! Messages.AcceptedBoardsWerePublishedToFile
      context.stop(self)
    }

    case ps: Messages.ProblemSettings ⇒ startTime = System.currentTimeMillis

    case message                      ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }

}