package as.chessproblem.application.publisher.file

import com.typesafe.config.Config
import as.chess.problem.drawer.AsciiDefinitions

object AcceptedBoardsFilePublisherConfig {

  final val safeConfigKey = "safe"
  final val unsafeConfigKey = "unsafe"
  final val emptyConfigKey = "empty"
  final val kingConfigKey = "king"
  final val queenConfigKey = "queen"
  final val rookConfigKey = "rook"
  final val bishopConfigKey = "bishop"
  final val knightConfigKey = "knight"
  final val fileConfigKey = "file"
  final val appendConfigKey = "append"
  final val bufferSizeInBytesConfigKey = "bufferSizeInBytes"

  def apply(config: Config): AcceptedBoardsFilePublisherConfig = {

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

    val file = config.getString(fileConfigKey)
    val append = config.getBoolean(appendConfigKey)
    val bufferSizeInBytes = config.getInt(bufferSizeInBytesConfigKey)

    new AcceptedBoardsFilePublisherConfig(asciiDefinitions, file, append, bufferSizeInBytes)
  }
}

case class AcceptedBoardsFilePublisherConfig(asciiDefinitions: AsciiDefinitions, file: String, append: Boolean, bufferSizeInBytes: Int)