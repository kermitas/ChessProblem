package as.chessproblem.transform

import akka.actor._
import as.akka.broadcaster.Broadcaster
import as.ama.startup.InitializationResult
import as.chessproblem.Messages
import com.typesafe.config.Config
import as.chess.problem.board.Board

class UniqueBoardTransformer(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    try {
      broadcaster ! new Broadcaster.Register(self, new BoardTransformerClassifier)
      broadcaster ! new InitializationResult(Right(None))
    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'unique board transform'.", e)))
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {
    case Messages.GeneratedUniqueBoard(uniqueBoard) ⇒ boardTransformer(uniqueBoard).foreach(broadcaster ! new Messages.GeneratedBoard(_))

    case Messages.AllUniqueBoardsWereGenerated ⇒ {
      broadcaster ! Messages.AllBoardsWereGenerated
      context.stop(self)
    }

    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }

  protected def boardTransformer(board: Board): Seq[Board] = {

    val uniqueBoards = new scala.collection.mutable.ListBuffer[Board]

    def addIfUnique(board: Board) {
      if (uniqueBoards.find(_.equals(board)).isEmpty) uniqueBoards += board
    }

    def addWithVerticalMirroring(board: Board) {
      addIfUnique(board)
      addIfUnique(board.mirrorVertically)
    }

    addWithVerticalMirroring(board)

    if (board.width == board.height) {
      addWithVerticalMirroring(board.rotateClockwise(1))
      addWithVerticalMirroring(board.rotateClockwise(2))
      addWithVerticalMirroring(board.rotateClockwise(3))

    } else {
      addIfUnique(board.mirrorHorizontally)
      addIfUnique(board.mirrorVerticallyAndHorizontally)
    }

    uniqueBoards
  }
}