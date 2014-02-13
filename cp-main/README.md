ChesssProblem: cp-mian project
==============================


Testing instructions (SBT):
---------------------------

Big boards take a lot of time. You can exclude big boards tests by:
<p>sbt test-only * -- -l "BigBoardsTest BigQueensTest"</p>