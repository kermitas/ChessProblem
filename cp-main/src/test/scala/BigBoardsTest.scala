import as.chess.problem.board.path.BlacklistedPaths
import org.scalatest._

import as.chess.problem.piece._
import as.chess.problem.board.{ UniqueBoardsGenerator, Board }

class BigBoardsTest extends FeatureSpec with GivenWhenThen with Matchers {

  val workMode = BlacklistedPaths.CpuWorkMode

  scenario("advanced board scenario: 8x8 board 8x Queen", BigBoardsTestTag, BigQueensTestTag) {
    Given("board (8,8)")
    val board = new Board(8, 8)
    And("8x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream, workMode)
    Then("result should be 12 unique board")
    boardsStream.filter(_.isDefined).size should be(12)
  }

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
