import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform._
import xerial.sbt.Pack._

object Build extends Build {

  lazy val mc = "as.ama.Main"

  lazy val projectSettings = Defaults.defaultSettings ++ packSettings  ++ Seq (
    name := "cp-main",
    version := "0.5.0",
    organization := "as.chessproblem",
    scalaVersion := "2.10.3",
    mainClass in (Compile,run) := Some(mc),
    scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation"),
    resolvers += Classpaths.typesafeReleases,
    resolvers += Classpaths.typesafeSnapshots,
    libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.3.0-RC2",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13",
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test",
    libraryDependencies += "as.ama" %% "ama-core" % "0.4.0",
    packMain := Map("run" -> mc)
  ) ++ scalariformSettings ++ formattingPreferences

  // Have to define dependency to AkkaMicroArchitecture as libraryDependencies because it looks like
  // aggregated/dependsOn project can not be downloaded from http://.
  // My post: https://groups.google.com/d/msg/sbt-dev/x2unE0p6_94/TIJOnkRpYC8J
  // libraryDependencies += "as.ama" %% "ama-core" % "0.4.0",

  def formattingPreferences = {
    import scalariform.formatter.preferences._
    ScalariformKeys.preferences := FormattingPreferences()
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, false)
  }

  lazy val cpMain = Project(
      id = "cp-main",
      base = file("."),
      settings = projectSettings
    ).dependsOn(cpChessClassic, cpChessProblem).aggregate(cpChessClassic, cpChessProblem)

  lazy val cpChessClassic = RootProject(file("../cp-chess-classic"))
  lazy val cpChessProblem = RootProject(file("../cp-chess-problem"))
}
