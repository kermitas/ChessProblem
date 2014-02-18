import org.scalatest._
import as.chess.problem.piece._
import as.chess.problem.board.{ BoardsGenerator, Board }

class BigBoardsTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("big board scenario: 8x8 board 8x Queen", BigBoardsTestTag, BigQueensTestTag) {
    Given("board (8,8)")
    val board = new Board(8, 8)
    And("8x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream, 1).head
    Then("result should be 92 unique board")
    boardsStream.filter(_.isDefined).size should be(92)
  }
}
