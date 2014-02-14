import as.chess.problem.piece.{ Rook, Queen, King, PositionedPiece }
import org.scalatest.{ Matchers, GivenWhenThen, FeatureSpec }
import as.chess.problem.board.path.BlacklistedPaths

class BlacklistedPathsTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("blacklisting path for board 8x8", BlacklistedPathsTestTag) {

    Given("BlacklistedPaths for board 8x8")

    val bl = new BlacklistedPaths(8, 8)

    val path = List[PositionedPiece](
      new PositionedPiece(0, 0, King.king),
      new PositionedPiece(1, 1, Queen.queen),
      new PositionedPiece(2, 2, Rook.rook))

    And("path that will be blacklisted /" + path.mkString("/"))

    When("we blacklist this path")
    bl.blacklist(path)

    Then("result should be 4")

    val paths = bl.getPaths
    paths.foreach(pp ⇒ println("/" + pp.mkString("/")))

    paths.size should be(4)
  }
}