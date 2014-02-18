import org.scalatest._

import as.chess.problem.piece._
import as.chess.problem.board.{ UniqueBoardsGenerator, BoardsGenerator, Board }

class SmallBoardsTest extends FeatureSpec with GivenWhenThen with Matchers {

  final val printBoards = false

  scenario("basic board scenario: 2x2 board 1x LightFlyer", SmallBoardsTestTag) {
    Given("board (2,2)")
    val board = new Board(2, 2)
    And("1x LightFlyer")
    val piecesStream = List[Piece](LightFlyer.lightFlyer).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 4 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(4)
  }

  scenario("basic board scenario: 2x2 board 2x LightFlyer", SmallBoardsTestTag) {
    Given("board (2,2)")
    val board = new Board(2, 2)
    And("2x LightFlyer")
    val piecesStream = List[Piece](LightFlyer.lightFlyer, LightFlyer.lightFlyer).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 6 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(6)
  }

  scenario("basic board scenario: 3x3 board 1x King", SmallBoardsTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("1x King")
    val piecesStream = List[Piece](King.king).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 9 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(9)
  }

  scenario("basic board scenario: 3x3 board 2x King", SmallBoardsTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2x King")
    val piecesStream = List[Piece](King.king, King.king).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 16 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(16)
  }

  scenario("basic board scenario: 3x3 board 2x King, 1x Rook", SmallBoardsTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2x King, 1x Rook")
    val piecesStream = List[Piece](King.king, King.king, Rook.rook).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 4 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(4)
  }

  scenario("basic board scenario: 4x4 board 4x Knight, 2x Rook", SmallBoardsTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Knight, 2x Rook")
    val piecesStream = List[Piece](Knight.knight, Knight.knight, Knight.knight, Knight.knight, Rook.rook, Rook.rook).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 8 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(8)
  }

  scenario("basic board scenario: 4x4 board 4x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 2 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(2)
  }

  scenario("basic scenario: 5x5 board 5x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (5,5)")
    val board = new Board(5, 5)
    And("5x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 10 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(10)
  }

  scenario("basic scenario: 6x6 board 6x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (6,6)")
    val board = new Board(6, 6)
    And("6x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 4 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(4)
  }

  scenario("basic scenario: 7x7 board 7x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (7,7)")
    val board = new Board(7, 7)
    And("7x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = BoardsGenerator.generateBoardsStream(board, piecesStream)
    Then("result should be 40 (non unique) boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(40)
  }

  /*
  scenario("basic board scenario: 2x2 board 1x LightFlyer", SmallBoardsTestTag) {
    Given("board (2,2)")
    val board = new Board(2, 2)
    And("1x LightFlyer")
    val piecesStream = List[Piece](LightFlyer.lightFlyer).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 1 unique board")
    boardsStream.filter(_.isDefined).size should be(1)
  }

  scenario("basic board scenario: 2x2 board 2x LightFlyer", SmallBoardsTestTag) {
    Given("board (2,2)")
    val board = new Board(2, 2)
    And("2x LightFlyer")
    val piecesStream = List[Piece](LightFlyer.lightFlyer, LightFlyer.lightFlyer).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 2 unique boards")
    boardsStream.filter(_.isDefined).size should be(2)
  }

  scenario("basic board scenario: 3x3 board 1x King", SmallBoardsTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("1x King")
    val piecesStream = List[Piece](King.king).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 3 unique board")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(3)
  }

  scenario("basic board scenario: 3x3 board 2x King", SmallBoardsTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2x King")
    val piecesStream = List[Piece](King.king, King.king).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 4 unique boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(4)
  }

  scenario("basic board scenario: 3x3 board 2x King, 1x Rook", SmallBoardsTestTag) {
    Given("board (3,3)")
    val board = new Board(3, 3)
    And("2x King, 1x Rook")
    val piecesStream = List[Piece](King.king, King.king, Rook.rook).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 1 unique board")
    boardsStream.filter(_.isDefined).size should be(1)
  }

  scenario("basic board scenario: 4x4 board 4x Knight, 2x Rook", SmallBoardsTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Knight, 2x Rook")
    val piecesStream = List[Piece](Knight.knight, Knight.knight, Knight.knight, Knight.knight, Rook.rook, Rook.rook).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 2 unique boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(2)
  }

  scenario("basic board scenario: 4x4 board 4x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (4,4)")
    val board = new Board(4, 4)
    And("4x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 1 unique board")
    boardsStream.filter(_.isDefined).size should be(1)
  }

  scenario("basic scenario: 5x5 board 5x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (5,5)")
    val board = new Board(5, 5)
    And("5x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 2 unique boards")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(2)
  }

  scenario("basic scenario: 6x6 board 6x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (6,6)")
    val board = new Board(6, 6)
    And("6x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 1 unique board")

    val readyBoards = boardsStream.filter(_.isDefined).map(_.get)
    if (printBoards) readyBoards.foreach(b ⇒ println(as.chess.problem.drawer.AsciiDrawer.draw(b)))

    readyBoards.size should be(1)
  }

  scenario("basic scenario: 7x7 board 7x Queen", SmallBoardsTestTag, SmallQueensTestTag) {
    Given("board (7,7)")
    val board = new Board(7, 7)
    And("7x Queen")
    val piecesStream = List[Piece](Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen, Queen.queen).toStream
    When("we generate boards")
    val boardsStream = UniqueBoardsGenerator.generateUniqueBoardsStream(board, piecesStream)
    Then("result should be 6 unique board")
    boardsStream.filter(_.isDefined).size should be(6)
  }
  */
}
