lazy val root = (project in file("."))
  .aggregate(`akka-http`, configs, core, doc, `jsoniter-scala`, quill)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  // root intentionally does not contain any code, so don't publish
  .settings(CommonSettingsPlugin.publishSettings(enabled = false))
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    // https: //www.scala-sbt.org/1.x/docs/Cross-Build.html#Cross+building+a+project
    crossScalaVersions := Nil,
    name := "structible-root"
  )

lazy val `akka-http` = (project in file("akka-http"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(CommonSettingsPlugin.publishSettings(enabled = true))
  .settings(
    crossScalaVersions := CommonSettingsPlugin.crossScalaVersions,
    libraryDependencies ++= Dependencies.`akka-http`,
    name := "structible-akka-http",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val configs = (project in file("configs"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(CommonSettingsPlugin.publishSettings(enabled = true))
  .settings(
//    TODO: quill has not yet been published for Scala 2.13
//    crossScalaVersions := List(CommonSettingsPlugin.scala212VersionValue),
//    scalaVersion := CommonSettingsPlugin.scala212VersionValue,
    libraryDependencies ++= Dependencies.configs,
    name := "structible-configs",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val core = (project in file("core"))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(CommonSettingsPlugin.publishSettings(enabled = true))
  .settings(
    crossScalaVersions := CommonSettingsPlugin.crossScalaVersions,
    libraryDependencies ++= Dependencies.core,
    name := "structible-core",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

// if makeSite fails, make sure that the Scala 2.12 compile options are removed (seems like the build is not reloaded on Scala version switch as part of cross-builds)
lazy val doc = (project in file("doc"))
  .dependsOn(`akka-http`, configs, core, `jsoniter-scala`, quill)
  .enablePlugins(GhpagesPlugin, GitBranchPrompt, GitVersioning, ParadoxSitePlugin, ParadoxPlugin, PreprocessPlugin)
  // doc/src contains example code only to embedded in the documentation, so don't publish
  .settings(CommonSettingsPlugin.publishSettings(enabled = false))
  // all these settings are only relevant to the "doc" project which is why they are not defined in CommonSettingsPlugin.scala
  .settings(
    // make sure that the example codes compiles in all cross Scala versions
//    TODO: configs, quill has not yet been published for Scala 2.13
//    crossScalaVersions := CommonSettingsPlugin.crossScalaVersions,
    // only delete index.html which to put a new latest version link in to place but retain the old doc
    includeFilter in ghpagesCleanSite := "index.html",
    libraryDependencies ++= Dependencies.doc,
    name := "structible-doc",
    // trigger dump-license-report in all other projects and rename the output
    // (paradox uses the first heading as link name in '@@@index' containers AND cannot handle variables in links)
    (mappings in Compile) in paradoxMarkdownToHtml ++= Seq(
      (`akka-http` / dumpLicenseReport).value / ((`akka-http` / licenseReportTitle).value + ".md") -> "licenses/akka-http.md",
      (configs / dumpLicenseReport).value / ((configs / licenseReportTitle).value + ".md") -> "licenses/configs.md",
      (core / dumpLicenseReport).value / ((core / licenseReportTitle).value + ".md") -> "licenses/core.md",
      (`jsoniter-scala` / dumpLicenseReport).value / ((`jsoniter-scala` / licenseReportTitle).value + ".md") -> "licenses/jsoniter-scala.md",
      (quill / dumpLicenseReport).value / ((quill / licenseReportTitle).value + ".md") -> "licenses/quill.md"
    ),
    // trigger code compilation of example code
    paradox in Compile := {
      val _ = (compile in Compile).value
      (paradox in Compile).value
    },
    // properties to be accessible from within the documentation
    paradoxProperties ++= Map(
      "group" -> organization.value,
      "name.akka-http" -> (`akka-http` / name).value,
      "name.configs" -> (configs / name).value,
      "name.core" -> (core / name).value,
      "name.jsoniter-scala" -> (`jsoniter-scala` / name).value,
      "name.quill" -> (quill / name).value,
      "version" -> version.value
    ),
    paradoxTheme := Some(builtinParadoxTheme("generic")),
    // used to update the "latest" link in the doc index.html which is not managed by paradox
    preprocessVars in Preprocess := Map("version" -> version.value),
    // move the paradox source into a sub-directory named after the current version
    siteSubdirName in Paradox := version.value
  )

lazy val `jsoniter-scala` = (project in file("jsoniter-scala"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(CommonSettingsPlugin.publishSettings(enabled = true))
  .settings(
    crossScalaVersions := CommonSettingsPlugin.crossScalaVersions,
    libraryDependencies ++= Dependencies.`jsoniter-scala`,
    name := "structible-jsoniter-scala",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val quill = (project in file("quill"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(CommonSettingsPlugin.publishSettings(enabled = true))
  .settings(
//    TODO: quill has not yet been published for Scala 2.13
//    crossScalaVersions := CommonSettingsPlugin.crossScalaVersions,
//    scalaVersion := CommonSettingsPlugin.scala212VersionValue,
    libraryDependencies ++= Dependencies.quill,
    name := "structible-quill",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
