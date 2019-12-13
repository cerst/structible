import sbt.Keys._
import sbt._
import xerial.sbt.Sonatype.autoImport.sonatypePublishToBundle

object PublishSettings {

  def apply(enabled: Boolean): Seq[Def.Setting[_]] = {
    if (!enabled) {
      Seq(skip in publish := true)
    } else {
      Seq(
        developers := List(Developer("cerst", "Constantin Gerstberger", "", url("https://github.com/cerst"))),
        homepage := Some(CommonValues.homepage),
        // keep consistent with DefaultSettingsPlugin.sbtHeaderSettings
        licenses += "MIT" -> url("https://opensource.org/licenses/MIT"),
        publishMavenStyle := true,
        publishTo := sonatypePublishToBundle.value,
        scmInfo := Some(ScmInfo(CommonValues.homepage, CommonValues.connection))
      )
    }
  }

}
