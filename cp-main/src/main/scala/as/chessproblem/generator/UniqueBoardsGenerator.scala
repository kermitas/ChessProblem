package as.chessproblem.generator

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.Board
import as.ama.startup._
import com.typesafe.config.Config
import as.ama.addon.lifecycle.LifecycleListener
import as.chess.problem.board.path.BlacklistedPaths

object UniqueBoardsGenerator {
  final val workModeConfigKey = "workMode"
}

class UniqueBoardsGenerator(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  import UniqueBoardsGenerator._

  protected var workMode: BlacklistedPaths.WorkMode = _

  override def preStart() {
    try {

      workMode = BlacklistedPaths.getWorkMode(config.getString(workModeConfigKey))

      broadcaster ! new Broadcaster.Register(self, new UniqueBoardsGeneratorClassifier)

      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing boards generator.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.ProblemSettings(board, pieces) ⇒ self ! as.chess.problem.board.UniqueBoardsGenerator.generateUniqueBoardsStream(board, pieces.toStream, workMode)

    case boardsStream: Stream[_]                 ⇒ pullBoardFromTheStreamAndContinueOrStop(boardsStream.asInstanceOf[Stream[Option[Board]]])

    case ss: LifecycleListener.ShutdownSystem    ⇒ context.stop(self)

    case message                                 ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }

  protected def pullBoardFromTheStreamAndContinueOrStop(boardsStream: Stream[Option[Board]]) {
    boardsStream match {

      case boardOption #:: restOfBoards ⇒ {

        boardOption match {

          case Some(board) ⇒ {
            broadcaster ! new Messages.GeneratedUniqueBoard(board)
            self ! restOfBoards
          }

          case None ⇒ self ! restOfBoards
        }
      }

      case _ ⇒ {
        broadcaster ! Messages.AllUniqueBoardsWereGenerated
        context.stop(self)
      }
    }
  }
}