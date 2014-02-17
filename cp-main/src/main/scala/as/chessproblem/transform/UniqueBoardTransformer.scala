package as.chessproblem.transform

import scala.collection.GenSeq
import scala.collection.parallel.mutable.ParArray
import akka.actor._
import as.akka.broadcaster.Broadcaster
import as.ama.startup.InitializationResult
import as.chessproblem.Messages
import com.typesafe.config.Config
import as.chess.problem.board.Board
import as.chess.problem.geom.transform._

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

  protected def boardTransformer(board: Board): GenSeq[Board] = {

    var uniqueBoards = ParArray[Board]()

    def addIfUnique(board: Board) {
      uniqueBoards.find(_.equals(board)).getOrElse(uniqueBoards = uniqueBoards :+ board)
    }

    def addWithVerticalMirroring(board: Board) {
      addIfUnique(board)
      addIfUnique(board.mirrorVertically)
    }

    addWithVerticalMirroring(board)

    if (board.width == board.height) {
      addWithVerticalMirroring(board.rotateClockwise(ClockwiseQuadrantRotation90))
      addWithVerticalMirroring(board.rotateClockwise(ClockwiseQuadrantRotation180))
      addWithVerticalMirroring(board.rotateClockwise(ClockwiseQuadrantRotation270))

    } else {
      addIfUnique(board.mirrorHorizontally)
      addIfUnique(board.mirrorVerticallyAndHorizontally)
    }

    uniqueBoards
  }
}