package as.chess.problem.drawer

import as.chess.problem.board.Board

import as.chess.classic.field.{ Empty, Occupied }
import as.chess.problem.field.{ Safe, Unsafe }
import as.chess.problem.piece._

case class AsciiDefinitions(safe: String, unsafe: String, empty: String, king: String, queen: String, rook: String, bishop: String, knight: String, lightFlyer: String = " LF ") extends Serializable

object AsciiDrawer {

  final val defaultAsciiDefinitions = new AsciiDefinitions(" .. ", " ~~ ", " :: ", " Ki ", " Q  ", " R  ", " B  ", " Kn ")

  def draw(b: Board, asciiDefinitions: AsciiDefinitions = defaultAsciiDefinitions): String = {
    val sb = new StringBuilder

    sb.append(System.lineSeparator)
    for (x ← 0 until b.width) sb.append("+----")
    sb.append("+").append(System.lineSeparator)

    for (y ← 0 until b.height) {
      sb.append("|")

      for (x ← 0 until b.width) {

        // Look out! Use of bare array! If you don't want this use: b.get(x, y) match{ Left, Right }, see commented code below.
        b.getArray(y)(x) match {

          case o: Occupied[_] ⇒ o.piece match {
            case King.king             ⇒ sb.append(asciiDefinitions.king)
            case Queen.queen           ⇒ sb.append(asciiDefinitions.queen)
            case Rook.rook             ⇒ sb.append(asciiDefinitions.rook)
            case Bishop.bishop         ⇒ sb.append(asciiDefinitions.bishop)
            case Knight.knight         ⇒ sb.append(asciiDefinitions.knight)
            case LightFlyer.lightFlyer ⇒ sb.append(asciiDefinitions.lightFlyer) // just for test purposes
          }

          case Safe.safe     ⇒ sb.append(asciiDefinitions.safe)

          case Unsafe.unsafe ⇒ sb.append(asciiDefinitions.unsafe)

          case Empty.empty   ⇒ sb.append(asciiDefinitions.empty)
        }

        sb.append("|")
      }

      sb.append(System.lineSeparator)

      for (x ← 0 until b.width) sb.append("+----")
      sb.append("+").append(System.lineSeparator)
    }

    sb.toString
  }

  /*
  def draw(b: Board, safe: String, unsafe: String, empty: String): String = {
    val sb = new StringBuilder

    sb.append(System.lineSeparator)
    for (x ← 0 until b.width) sb.append("+----")
    sb.append("+").append(System.lineSeparator)

    for (y ← 0 until b.height) {
      sb.append("|")

      for (x ← 0 until b.width) {
        b.get(x, y) match {

          case Left(e) ⇒

          case Right(field) ⇒ field match {
            case o: Occupied[_] ⇒ o.piece match {
              case King.king     ⇒ sb.append(" Ki ")
              case Queen.queen   ⇒ sb.append(" Q  ")
              case Rook.rook     ⇒ sb.append(" R  ")
              case Bishop.bishop ⇒ sb.append(" B  ")
              case Knight.knight ⇒ sb.append(" Kn ")
            }

            case Safe.safe     ⇒ sb.append(safe)

            case Unsafe.unsafe ⇒ sb.append(unsafe)

            case Empty.empty   ⇒ sb.append(empty)
          }
        }

        sb.append("|")
      }

      sb.append(System.lineSeparator)

      for (x ← 0 until b.width) sb.append("+----")
      sb.append("+").append(System.lineSeparator)
    }

    sb.toString
  }
  */
}
