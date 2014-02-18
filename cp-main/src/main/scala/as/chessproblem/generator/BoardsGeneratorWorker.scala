package as.chessproblem.generator

import akka.actor.{ ActorLogging, Actor, ActorRef, Props }
import as.chess.problem.board.Board
import as.ama.addon.lifecycle.LifecycleListener
import as.chessproblem.Messages
import as.akka.broadcaster.Broadcaster

/**
 * Pulls all boards from non blocking stream (Stream[Option[Board]])
 *
 * Die after reach end of the stream.
 */
object BoardsGeneratorWorker {
  def props(broadcaster: ActorRef): Props = Props(new BoardsGeneratorWorker(broadcaster))
}

class BoardsGeneratorWorker(broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    broadcaster ! new Broadcaster.Register(self, new BoardsGeneratorWorkerClassifier)
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {

    case boardsStream: Stream[_]              ⇒ pullBoardFromTheStreamThenContinueOrStop(boardsStream.asInstanceOf[Stream[Option[Board]]])
    case ss: LifecycleListener.ShutdownSystem ⇒ context.stop(self)
    case message                              ⇒ log.warning(s"Unhandled $message send by ${sender()}")
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

      case _ ⇒ context.stop(self)
    }
  }
}