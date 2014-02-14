import org.scalatest._

import as.chess.problem.piece._
import as.chess.problem.board.{ UniqueBoardsGenerator, Board }
import as.chess.problem.board.path.BlacklistedPaths

class SmallBoardsTestWithPermutedPieces extends FeatureSpec with GivenWhenThen with Matchers {

  val workMode = BlacklistedPaths.MemoryWorkMode

  scenario("A-1: pieces permutation scenario for basic board: 3x3 board 2x King, 1x Rook", SmallBoardsWithPermutedPiecesTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2x King, 1x Rook")
    val piecesStream = List[Piece](King.king, Rook.rook, King.king).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream, workMode)
    Then("result should be 1 unique board")
    boardsStream.filter(_.isDefined).size should be(1)
  }

  scenario("A-2: pieces permutation scenario for basic board: 3x3 board 2x King, 1x Rook", SmallBoardsWithPermutedPiecesTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2x King, 1x Rook")
    val piecesStream = List[Piece](Rook.rook, King.king, King.king).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream, workMode)
    Then("result should be 1 unique board")
    boardsStream.filter(_.isDefined).size should be(1)
  }

  scenario("B-1: pieces permutation scenario for basic board: 4x4 board 4x Knight, 2x Rook", SmallBoardsWithPermutedPiecesTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Knight, 2x Rook")
    val piecesStream = List[Piece](Rook.rook, Knight.knight, Knight.knight, Rook.rook, Knight.knight, Knight.knight).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream, workMode)
    Then("result should be 2 unique boards")
    boardsStream.filter(_.isDefined).size should be(2)
  }

  scenario("B-2: pieces permutation scenario for basic board: 4x4 board 4x Knight, 2x Rook", SmallBoardsWithPermutedPiecesTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Knight, 2x Rook")
    val piecesStream = List[Piece](Rook.rook, Rook.rook, Knight.knight, Knight.knight, Knight.knight, Knight.knight).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream, workMode)
    Then("result should be 2 unique boards")
    boardsStream.filter(_.isDefined).size should be(2)
  }

  scenario("B-3: pieces permutation scenario for basic board: 4x4 board 4x Knight, 2x Rook", SmallBoardsWithPermutedPiecesTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Knight, 2x Rook")
    val piecesStream = List[Piece](Knight.knight, Knight.knight, Rook.rook, Rook.rook, Knight.knight, Knight.knight).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream, workMode)
    Then("result should be 2 unique boards")
    boardsStream.filter(_.isDefined).size should be(2)
  }

  scenario("B-4: pieces permutation scenario for basic board: 4x4 board 4x Knight, 2x Rook", SmallBoardsWithPermutedPiecesTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Knight, 2x Rook")
    val piecesStream = List[Piece](Knight.knight, Rook.rook, Knight.knight, Rook.rook, Knight.knight, Knight.knight).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream, workMode)
    Then("result should be 2 unique boards")
    boardsStream.filter(_.isDefined).size should be(2)
  }
}
