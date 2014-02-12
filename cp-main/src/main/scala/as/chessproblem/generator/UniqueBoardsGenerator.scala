package as.chessproblem.generator

import akka.actor._
import as.akka.broadcaster._
import as.chessproblem.Messages
import as.chess.problem.board.{ Board, BoardGenerator }
import as.ama.startup._
import com.typesafe.config.Config
import as.ama.addon.lifecycle.LifecycleListener

class UniqueBoardsGenerator(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new UniqueBoardsGeneratorClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing boards generator.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case Messages.ProblemSettings(board, pieces) ⇒ self ! BoardGenerator.generateBoardsStream(board, pieces.toStream)

    case boardsStream: Stream[_]                 ⇒ pullBoardFromTheStreamAndContinueOrStop(boardsStream.asInstanceOf[Stream[Option[Board]]])

    case ss: LifecycleListener.ShutdownSystem    ⇒ context.stop(self)

    case message                                 ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }

  protected def pullBoardFromTheStreamAndContinueOrStop(boardsStream: Stream[Option[Board]]) {
    boardsStream match {

      case boardOption #:: restOfBoards ⇒ {

        boardOption match {

          case Some(board) ⇒ {
            //println("SmartGameEvaluator: ... received Some(board)")

            broadcaster ! new Messages.GeneratedUniqueBoard(board)

            self ! restOfBoards
          }

          case None ⇒ {
            //println("SmartGameEvaluator: ... received None")
            self ! restOfBoards
          }
        }

      }

      case _ ⇒ {
        //println("SmartGameEvaluator: ... no boards in stream, finishing")
        broadcaster ! Messages.AllUniqueBoardsWereGenerated
        context.stop(self)
      }
    }
  }
}