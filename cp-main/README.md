ChesssProblem: cp-mian project
==============================

### Compile (SBT):

**AkkaMicroArchitecture**

Project is build on top of AkkaMicroArchitecture (https://github.com/kermitas/AkkaMicroArchitecture). To install AkkaMicroArchitecture in your local repository you have to:
- clone it on your local drive: `git clone https://github.com/kermitas/AkkaMicroArchitecture`
- go to ama-core folder `cd ama-core`
- start SBT `sbt`
- execute `compile` command
- and finally publish it into you local repository `publish-local`
- now you can quit SBT `exit`

**ChessProblem**

- go to cp-main project folder `cd cp-main`
- start SBT `sbt`
- execute `compile` command
- if you are using IntelliJ Idea you can generate Idea project by `gen-idea`
- to create distribution `pack` (generated distribution will be located at `target/pack` folder)
- to quit SBT `exit`

You can use ready distribution without SBT, it is located at `cp-main/distribution`.

### Run (under SBT):
- go to cp-main project folder `cd cp-main`
- start SBT `sbt`
- to run with board 3x3 and one King `run 3 3 1 King`

### Run (standalone):
- go to cp-main project folder `cd cp-main`
- go to folder with distribution `cd distribution`
- run scripts (for Linux and for Windows) are in `bin` folder
- start `./bin/run 3 3 1 King` or `bin\run.bat 3 3 1 King`

### Testing instructions (SBT):

Big boards take a lot of time. You can exclude big boards tests (BigBoardsTest and BigQueensTest) by:
`sbt test-only * -- -l "BigBoardsTest BigQueensTest VeryBigBoardsTest VeryBigQueensTest"`

To run just one test (for example SmallBoardsTest):
`sbt test-only * -- -n SmallBoardsTest`