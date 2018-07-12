import com.typesafe.sbt.GitPlugin.autoImport.git
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.{
  HeaderLicense,
  headerLicense
}
import sbt._
import sbt.Keys._

object CommonSettingsPlugin extends CommonSettingsPluginTpl {

  // the rationale for placing settings defs here is that they should (or can) not be updated automatically using the scala-base-sync script
  // in the following, organizationName and startYear would also be required by sbt-header to generate ready-made license headers
  override lazy val projectSettings: Seq[Def.Setting[_]] = {
    tplProjectSettingsPlus(
      git.baseVersion := "0.0.0",
      headerLicense := Some(
        HeaderLicense.MIT(startYear.value.get.toString, organizationName.value)
      ),
      licenses += "MIT" -> url("https://opensource.org/licenses/MIT"),
      organization := "io.cerst",
      organizationName := "Constantin Gerstberger",
      resolvers ++= Dependencies.resolvers,
      startYear := Some(2018)
    )
  }
}
