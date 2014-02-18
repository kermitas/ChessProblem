import org.scalatest._
import as.chess.problem.board.{ BoardsGenerator, Board }
import as.chess.problem.piece._

class VeryBigBoardsTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("big board scenario: 7x7 board 2x King 2x Bishop 2x Queen 1x Knight", VeryBigBoardsTestTag) {
    Given("board (7,7)")
    val board = new Board(7, 7)
    And("2x King 2x Bishop 2x Queen 1x Knight")

    val piecesStream = List[Piece](Queen.queen, Queen.queen, Bishop.bishop, Bishop.bishop, King.king, King.king, Knight.knight).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream, 1).head
    Then("result should be 3063828 unique board")
    boardsStream.filter(_.isDefined).size should be(3063828)
  }

  scenario("big board scenario: 9x9 board 9x Queen", VeryBigBoardsTestTag, VeryBigQueensTestTag) {
    Given("board (9,9)")
    val board = new Board(9, 9)
    And("9x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream, 1).head
    Then("result should be 352 unique board")
    boardsStream.filter(_.isDefined).size should be(352)
  }

}
