import org.scalatest.Tag

object SmallQueensTestTag extends Tag("SmallQueensTest")
object BigQueensTestTag extends Tag("BigQueensTest")

object SmallBoardsTestTag extends Tag("SmallBoardsTest")
object BigBoardsTestTag extends Tag("BigBoardsTest")

object SmallBoardsWithPermutedPiecesTestTag extends Tag("SmallBoardsWithPermutedPiecesTest")

object PathPermutationsTestTag extends Tag("PathPermutationsTest")

// Will run only for those mentioned
//sbt test-only * -- -n "SmallBoardsTest PathPermutationsTest SmallBoardsWithPermutedPiecesTest"

// Will run all test except
//sbt test-only * -- -l "BigBoardsTest BigQueensTest"