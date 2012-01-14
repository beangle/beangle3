import sbt._
import Keys._
object BuildSettings {
  val buildOrganization = "Beangle"
  val buildVersion = "3.0.0-SNAPSHOT"
  val buildScalaVersion = "2.9.1"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion,
    shellPrompt  := ShellPrompt.buildShellPrompt
  )
}

object ShellPrompt {
  object devnull extends ProcessLogger {
    def info (s: => String) {}
    def error (s: => String) { }
    def buffer[T] (f: => T): T = f
  }
  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
  )

  val buildShellPrompt = { 
    (state: State) => {
      val currProject = Project.extract (state).currentProject.id
      "%s:%s:%s> ".format (
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}

object Dependencies {
  val h2Ver = "1.3.148"
  val slf4jVer = "1.6.1"
  val junitVer = "4.10"

  val slf4j = "org.slf4j" % "slf4j-api" % slf4jVer
  val slf4jJcl = "org.slf4j" % "jcl-over-slf4j" % slf4jVer
  val junit = "junit" % "junit" % junitVer % "test"
  val scalatest ="org.scalatest" % "scalatest_2.9.1" % "1.6.1" % "test"
}


object BeangleBuild extends Build {
  import Dependencies._
  import BuildSettings._

  val commonDeps= Seq (slf4j,slf4jJcl,junit,scalatest) 

  lazy val library: Project = Project("library", file(".")) aggregate(core)

  lazy val core: Project = Project(
    "core",
    file("org.beangle.core"),
    settings=buildSettings ++ Seq(libraryDependencies ++= commonDeps)
  )
}
