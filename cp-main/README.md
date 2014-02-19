ChesssProblem: cp-mian project
==============================

### Compile (SBT):

**AkkaMicroArchitecture**
Project is build on top of AkkaMicroArchitecture (https://github.com/kermitas/AkkaMicroArchitecture). To install AkkaMicroArchitecture in your local repository you have to:
- clone it on your local drive: `git clone https://github.com/kermitas/AkkaMicroArchitecture`
- go to ama-core folder `cd ama-core`
- start SBT `sbt`
- compile `compile`
- and finally publish it into you local repository `publish-local`
- now you can quit SBT `exit`

**ChessProblem**
To compile:
- go to cp-main project folder `cd cp-main`
- start SBT `sbt`
- compile `compile`
- if you are using IntelliJ Idea you can generate Idea project by `gen-idea`
- to create distribution `pack` (generated distribution will be located at `target/pack` folder)
- to quit SBT `exit`

### Run (under SBT):
To run:
- go to cp-main project folder `cd cp-main`
- start SBT `sbt`
- to run with board 3x3 and one King `run 3 3 1 King`

### Run (standalone):
Lets assume that you created distribution using `pack` command in SBT:
- go to cp-main project folder `cd cp-main`
- go to place where distribution is generated `cd target/pack`
- start `run 3 3 1 King` or `run.bat 3 3 1 King`

### Testing instructions (SBT):

Big boards take a lot of time. You can exclude big boards tests (BigBoardsTest and BigQueensTest) by:
`sbt test-only * -- -l "BigBoardsTest BigQueensTest VeryBigBoardsTest VeryBigQueensTest"`

To run just one test (for example SmallBoardsTest):
`sbt test-only * -- -n SmallBoardsTest`