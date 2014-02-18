ChesssProblem: cp-mian project
==============================


Testing instructions (SBT):
---------------------------

Big boards take a lot of time. You can exclude big boards tests (BigBoardsTest and BigQueensTest) by:
<p>sbt test-only * -- -l "BigBoardsTest BigQueensTest VeryBigBoardsTest VeryBigQueensTest"</p>

To run just one test (PathPermutationsTestTag):
<p>sbt test-only * -- -n SmallBoardsTest</p>