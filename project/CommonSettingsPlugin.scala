import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.{HeaderLicense, headerLicense}
import sbt.Keys._
import sbt._

object CommonSettingsPlugin extends CommonSettingsPluginTpl {

  lazy val scalaVersionValue = "2.12.10"
  lazy val crossScalaVersions = List(scalaVersionValue, "2.13.0")

  // the rationale for placing settings defs here is that they should (or can) not be updated automatically using the scala-base-sync script
  // in the following, organizationName and startYear would also be required by sbt-header to generate ready-made license headers
  override lazy val projectSettings: Seq[Def.Setting[_]] = {
    tplProjectSettingsPlus(scalaVersionValue)(
      developers := List(Developer("cerst", "Constantin Gerstberger", "", url("https://github.com/cerst"))),
      headerLicense := Some(HeaderLicense.MIT(startYear.value.get.toString, organizationName.value)),
      homepage := Some(url("https://github.com/cerst/structible")),
      licenses += "MIT" -> url("https://opensource.org/licenses/MIT"),
      organization := "com.github.cerst",
      organizationName := "Constantin Gerstberger",
      resolvers ++= Dependencies.resolvers,
      scmInfo := Some(ScmInfo(homepage.value.get, "git@github.com:cerst/structible.git")),
      startYear := Some(2018),
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
  }

  def publishSettings(enabled: Boolean): Seq[Def.Setting[_]] = {
    if (!enabled) {
      skip in publish := true
    } else {
      Seq(
        publishMavenStyle := true,
        publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)
      )
    }
  }

}
