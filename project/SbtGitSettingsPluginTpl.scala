import com.typesafe.sbt.GitVersioning
import com.typesafe.sbt.SbtGit.git
import sbt.{AutoPlugin, Def, Plugins}

/**
  * The GitVersioning plugin must be enabled explicitly, so its settings are bundled in a dedicated plugin.
  *
  * @see https://github.com/sbt/sbt-git
  */
object SbtGitSettingsPluginTpl extends AutoPlugin {

  override def requires: Plugins = GitVersioning

  override def trigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = {
    git.useGitDescribe := true
  }

}
