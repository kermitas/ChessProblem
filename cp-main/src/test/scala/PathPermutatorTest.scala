
import as.chess.problem.board.path.PathPermutator
import as.chess.problem.piece._
import org.scalatest.{ Matchers, GivenWhenThen, FeatureSpec }

class PathPermutatorTest extends FeatureSpec with GivenWhenThen with Matchers {

  val path = List[PositionedPiece](
    new PositionedPiece(0, 0, King.king),
    new PositionedPiece(1, 1, King.king),
    new PositionedPiece(2, 2, Queen.queen),
    new PositionedPiece(3, 3, Rook.rook),
    new PositionedPiece(4, 4, Rook.rook),
    new PositionedPiece(5, 5, Rook.rook),
    new PositionedPiece(6, 6, Bishop.bishop))

  scenario("A-1: find same elements count scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we check count starting on position 0")
    val sameCount = PathPermutator.getSameElementsLength(path, 0)

    Then("result should be 2")
    sameCount should be(2)
  }

  scenario("A-2: find same elements count scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we check count starting on position 1")
    val sameCount = PathPermutator.getSameElementsLength(path, 1)

    Then("result should be 1")
    sameCount should be(1)
  }

  scenario("A-3: find same elements count scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we check count starting on position 2")
    val sameCount = PathPermutator.getSameElementsLength(path, 2)

    Then("result should be 1")
    sameCount should be(1)
  }

  scenario("A-4: find same elements count scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we check count starting on position 3")
    val sameCount = PathPermutator.getSameElementsLength(path, 3)

    Then("result should be 3")
    sameCount should be(3)
  }

  scenario("B-1: permute sub-path scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we permute sub-path starting on position 0, elements count 1")
    val paths = PathPermutator.permutateSubPath(path, 0, 1)

    Then("result should be 1")
    paths.size should be(1)
  }

  scenario("B-2: permute sub-path scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we permute sub-path starting on position 0, elements count 2")
    val paths = PathPermutator.permutateSubPath(path, 0, 2)

    Then("result should be 2")
    paths.size should be(2)
  }

  scenario("B-3: permute sub-path scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we permute sub-path starting on position 3, elements count 3")
    val paths = PathPermutator.permutateSubPath(path, 3, 3)

    Then("result should be 6")
    paths.size should be(6)
  }

  scenario("C-1: permute all discovered sub-paths scenario") {

    Given("path /King/King/Queen/Rook/Rook/Rook/Bishop")

    When("we want to get all permutations of all sub-paths with the same elements")
    val paths = PathPermutator.getSubPathPermutations(path)

    Then("result should be 12")
    paths.size should be(12)
  }
}
