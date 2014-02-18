import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform._

object Build extends Build {

  lazy val projectSettings = Defaults.defaultSettings ++ Seq (
    name := "cp-chess-classic",
    version := "0.5.0",
    organization := "as.chessproblem",
    scalaVersion := "2.10.3",
    scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")
  ) ++ scalariformSettings ++ formattingPreferences

  def formattingPreferences = {
    import scalariform.formatter.preferences._
    ScalariformKeys.preferences := FormattingPreferences()
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, false)
  }

  lazy val chess = Project(
      id = "cp-chess-classic",
      base = file("."),
      settings = projectSettings
    )
}
