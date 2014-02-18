//import as.chess.problem.board.path.BlacklistedPaths
import org.scalatest._

import as.chess.problem.piece._
import as.chess.problem.board.{ BoardsGenerator, UniqueBoardsGenerator, Board }

class BigBoardsTest extends FeatureSpec with GivenWhenThen with Matchers {

  //val workMode = BlacklistedPaths.CpuWorkStrategy

  /*
  scenario("advanced board scenario: 8x8 board 8x Queen", BigBoardsTestTag, BigQueensTestTag) {
    Given("board (8,8)")
    val board = new Board(8, 8)
    And("8x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 92 unique board")
    boardsStream.filter(_.isDefined).size should be(92)
  }

  scenario("advanced board scenario: 9x9 board 9x Queen", BigBoardsTestTag, BigQueensTestTag) {
    Given("board (9,9)")
    val board = new Board(9, 9)
    And("8x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 352 unique board")
    boardsStream.filter(_.isDefined).size should be(352)
  }*/

  scenario("advanced board scenario: 7x7 board 2x King 2x Bishop 2x Queen 1x Knight", BigBoardsTestTag) {
    Given("board (7,7)")
    val board = new Board(7, 7)
    And("2x King 2x Bishop 2x Queen 1x Knight")

    val piecesStream = List[Piece](Queen.queen, Queen.queen, Bishop.bishop, Bishop.bishop, King.king, King.king, Knight.knight).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be ?? unique board")
    boardsStream.filter(_.isDefined).size should be(0)
  }

  /*
  scenario("advanced board scenario: 8x8 board 8x Queen", BigBoardsTestTag, BigQueensTestTag) {
    Given("board (8,8)")
    val board = new Board(8, 8)
    And("8x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 12 unique board")
    boardsStream.filter(_.isDefined).size should be(12)
  }
  */

  /*
  scenario("advanced board scenario: 9x9 board 9x Queen", BigQueensTest, BigBoardsTestTag) {
    Given("board (9,9)")
    val board = new Board(9, 9)
    And("9x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 46 unique board")
    boardsStream.filter(_.isDefined).size should be(46)
  }

  scenario("advanced board scenario: 10x10 board 10x Queen", BigQueensTest, BigBoardsTestTag) {
    Given("board (10,10)")
    val board = new Board(10, 10)
    And("10x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 92 unique board")
    boardsStream.filter(_.isDefined).size should be(92)
  }
  */
}
