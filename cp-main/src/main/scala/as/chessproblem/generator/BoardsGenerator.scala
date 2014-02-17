package as.chessproblem.generator

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.Board
import as.ama.startup._
import com.typesafe.config.Config
import as.ama.addon.lifecycle.LifecycleListener

class BoardsGenerator(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new BoardsGeneratorClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing boards generator.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.ProblemSettings(board, pieces) ⇒ self ! as.chess.problem.board.BoardsGenerator.generateBoardsStream(board, pieces.toStream)

    case boardsStream: Stream[_]                 ⇒ pullBoardFromTheStreamThenContinueOrStop(boardsStream.asInstanceOf[Stream[Option[Board]]])

    case ss: LifecycleListener.ShutdownSystem    ⇒ context.stop(self)

    case message                                 ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }

  protected def pullBoardFromTheStreamThenContinueOrStop(boardsStream: Stream[Option[Board]]) {
    boardsStream match {

      case boardOption #:: restOfBoards ⇒ {

        boardOption match {

          case Some(board) ⇒ {
            broadcaster ! new Messages.GeneratedBoard(board)
            self ! restOfBoards
          }

          case None ⇒ self ! restOfBoards
        }
      }

      case _ ⇒ {
        broadcaster ! Messages.AllBoardsWereGenerated
        context.stop(self)
      }
    }
  }
}