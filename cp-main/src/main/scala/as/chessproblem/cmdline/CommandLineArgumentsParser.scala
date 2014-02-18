package as.chessproblem.cmdline

import akka.actor._
import as.chessproblem._
import as.chess.problem.board.Board
import as.chess.problem.piece.Piece
import as.ama.startup._
import com.typesafe.config.Config

/**
 * Parse command line arguments and publish ProblemSettings on broadcaster.
 */
class CommandLineArgumentsParser(commandLineArguments: Array[String], config: Config, broadcaster: ActorRef) extends Actor with ActorLogging {

  override def preStart() {
    try {
      require(commandLineArguments.length >= 2, "Program should have at least two arguments (chess board dimensions).")
      require(commandLineArguments.length % 2 == 0, "Program should have even number of arguments: [[chess board dimension X] [chess board dimension Y]] [[piece count] [piece type]] [[piece count] [piece type]] ...")

      broadcaster ! parseCommandLineArguments(commandLineArguments)

      broadcaster ! new InitializationResult(Right(None))

    } catch {
      case e: Exception ⇒ broadcaster ! new InitializationResult(Left(new Exception("Problem while installing 'command line arguments parser'.", e)))
    } finally {
      context.stop(self)
    }
  }

  override def postRestart(throwable: Throwable) = preStart()

  override def receive = {
    case message ⇒ log.warning(s"Unhandled $message send by ${sender()}")
  }

  protected def parseCommandLineArguments(commandLineArguments: Array[String]) = new Messages.ProblemSettings(Board(commandLineArguments, 0), Piece(commandLineArguments, 2))
}