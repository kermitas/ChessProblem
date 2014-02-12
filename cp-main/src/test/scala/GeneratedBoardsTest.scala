import org.scalatest._

import as.chess.problem.piece._
import as.chess.problem.board.{ BoardGenerator, Board }

class GeneratedBoardsTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("scenario 1") {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2 Kings")
    val piecesStream = List[Piece](King.king, King.king).toStream
    When("we generate boards")
    val boardsStream = BoardGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 4 boards")
    boardsStream.filter(_.isDefined).size should be(4)
  }

  scenario("scenario 2") {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2 Kings 1 Rock")
    val piecesStream = List[Piece](King.king, King.king, Rook.rook).toStream
    When("we generate boards")
    val boardsStream = BoardGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 1 boards")
    boardsStream.filter(_.isDefined).size should be(1)
  }

}
