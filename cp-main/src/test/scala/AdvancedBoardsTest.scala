import org.scalatest._

import as.chess.problem.piece._
import as.chess.problem.board.{ UniqueBoardsGenerator, Board }

class AdvancedBoardsTest extends FeatureSpec with GivenWhenThen with Matchers {

  final val runAdvancedTests = false

  if (runAdvancedTests) {

    scenario("advanced board scenario: 8x8 board 8x Queen") {
      Given("board (8,8)")
      val board = new Board(8, 8)
      And("8x Queen")
      val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
      When("we generate boards")
      val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
      Then("result should be 12 unique board")
      boardsStream.filter(_.isDefined).size should be(12)
    }

  }

}
