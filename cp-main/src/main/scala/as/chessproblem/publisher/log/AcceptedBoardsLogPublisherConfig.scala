package as.chessproblem.publisher.log

import com.typesafe.config.Config
import as.chess.problem.drawer.AsciiDefinitions

object AcceptedBoardsLogPublisherConfig {

  final val safeConfigKey = "safe"
  final val unsafeConfigKey = "unsafe"
  final val emptyConfigKey = "empty"
  final val kingConfigKey = "king"
  final val queenConfigKey = "queen"
  final val rookConfigKey = "rook"
  final val bishopConfigKey = "bishop"
  final val knightConfigKey = "knight"
  final val drawBoardsAtTheEndConfigKey = "drawBoardsAtTheEnd"
  final val drawBoardsWhileProducedConfigKey = "drawBoardsWhileProduced"
  final val printProducedBoardNumberModuloFactorConfigKey = "printProducedBoardNumberModuloFactor"

  /**
   * Parsing configuration.
   */
  def apply(config: Config): AcceptedBoardsLogPublisherConfig = {

    val asciiDefinitions = {
      val safe = config.getString(safeConfigKey)
      val unsafe = config.getString(unsafeConfigKey)
      val empty = config.getString(emptyConfigKey)

      val king = config.getString(kingConfigKey)
      val queen = config.getString(queenConfigKey)
      val rook = config.getString(rookConfigKey)
      val bishop = config.getString(bishopConfigKey)
      val knight = config.getString(knightConfigKey)

      AsciiDefinitions(safe, unsafe, empty, king, queen, rook, bishop, knight)
    }

    val drawBoardsAtTheEnd = config.getBoolean(drawBoardsAtTheEndConfigKey)
    val drawBoardsWhileProduced = config.getBoolean(drawBoardsWhileProducedConfigKey)
    val printProducedBoardNumberModuloFactor = config.getInt(printProducedBoardNumberModuloFactorConfigKey)

    new AcceptedBoardsLogPublisherConfig(asciiDefinitions, drawBoardsWhileProduced, printProducedBoardNumberModuloFactor, drawBoardsAtTheEnd)
  }
}

/**
 * Configuration read from JSON (HOCON) file (by Typesafe Config library)
 */
case class AcceptedBoardsLogPublisherConfig(asciiDefinitions: AsciiDefinitions, drawBoardsWhileProduced: Boolean, printProducedBoardNumberModuloFactor: Int, drawBoardsAtTheEnd: Boolean)