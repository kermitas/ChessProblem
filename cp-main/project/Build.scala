import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform._
import xerial.sbt.Pack._

object Build extends Build {

  lazy val mc = "as.ama.Main"

  lazy val projectSettings = Defaults.defaultSettings ++ packSettings  ++ Seq (
    name := "cp-main",
    version := "0.4.0",
    organization := "as.chessproblem",
    scalaVersion := "2.10.3",
    mainClass in (Compile,run) := Some(mc),
    scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation"),
    resolvers += Classpaths.typesafeReleases,
    resolvers += Classpaths.typesafeSnapshots,
    libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.3.0-RC1",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13",
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

  // ===================== TMP / to delete =====================
  //lazy val sp = uri("https://github.com/typesafehub/sbteclipse")
  //lazy val ama = RootProject(uri("http://bitbucket.org/kermitas/akkamicroarchitecture/src/2e0934670affdacd9573cdba2951b47db80895e3/ama/?at=master"))
  //lazy val ama = ProjectRef(uri("http://bitbucket.org/kermitas/akkamicroarchitecture/src/2e0934670affdacd9573cdba2951b47db80895e3/ama/?at=master"), "ama")
  //lazy val ama = RootProject(uri("https://bitbucket.org/kermitas/akkamicroarchitecture/get/master.zip"))
  //lazy val ama = ProjectRef(uri("https://bitbucket.org/kermitas/akkamicroarchitecture/get/master.zip"), "master/ama")
  //lazy val ama = ProjectRef(file("http://bitbucket.org/kermitas/akkamicroarchitecture/get/master.zip"), "master")
  //lazy val ama = ProjectRef(uri("https://bitbucket.org/kermitas/akkamicroarchitecture/src/2e0934670affdacd9573cdba2951b47db80895e3/ama/?at=master"), "ama")
  //lazy val ama = uri("https://bitbucket.org/kermitas/akkamicroarchitecture/src/2e0934670affdacd9573cdba2951b47db80895e3/ama/?at=master")
  //lazy val ama = uri("file:///run/media/as/DATA/ScalaConsultants/AkkaMicroArchitecture/ama")
  //lazy val ama = ProjectRef(uri("https://bitbucket.org/kermitas/akkamicroarchitecture/src/2e0934670affdacd9573cdba2951b47db80895e3/ama"), "ama")
  //lazy val ama = RootProject(uri("git:git@bitbucket.org:kermitas/akkamicroarchitecture.git#2e0934670affdacd9573cdba2951b47db80895e3"))
  //lazy val ama = RootProject(uri("ssh://git@bitbucket.org:kermitas/akkamicroarchitecture.git"))
  //val resolver: sbt.BuildLoader.ResolveInfo => Option[() => java.io.File]  = Resolvers.remote
}
