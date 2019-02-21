def publishSettings(enabled: Boolean): Seq[Def.Setting[_]] = {
  if (!enabled) {
    Seq(skip in publish := true)
  } else {
    // refined as needed for publishing
    // publishTo := ???
    Seq()
  }
}

// TODO: set-up publish

lazy val root = (project in file("."))
  .aggregate(`akka-http`, configs, core, doc, `jsoniter-scala`, quill)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  // root intentionally does not contain any code, so don't publish
  .settings(publishSettings(enabled = false))
  .settings(name := "structible-root")

lazy val `akka-http` = (project in file("akka-http"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.`akka-http`,
    name := "structible-akka-http",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val configs = (project in file("configs"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.configs,
    name := "structible-configs",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val core = (project in file("core"))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.core,
    name := "structible-core",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val doc = (project in file("doc"))
  .dependsOn(`akka-http`, configs, core, `jsoniter-scala`, quill)
  .enablePlugins(GitBranchPrompt, GitVersioning, ParadoxPlugin)
  // doc/src contains example code only to embedded in the documentation, so don't publish
  .settings(publishSettings(enabled = false))
  // all these settings are only relevant to the "doc" project which is why they are not defined in CommonSettingsPlugin.scala
  .settings(
    libraryDependencies ++= Dependencies.doc,
    name := "structible-doc",
    // trigger dump-license-report in all other projects and rename the output
    // (paradox uses the first heading as link name in '@@@index' containers AND cannot handle variables in links)
    (mappings in Compile) in paradoxMarkdownToHtml ++= Seq(
      (`akka-http` / dumpLicenseReport).value / ((`akka-http` / licenseReportTitle).value + ".md") -> "licenses/akka-http.md",
      (configs / dumpLicenseReport).value / ((configs / licenseReportTitle).value + ".md") -> "licenses/configs.md",
      (core / dumpLicenseReport).value / ((core / licenseReportTitle).value + ".md") -> "licenses/core.md",
      dumpLicenseReport.value / (licenseReportTitle.value + ".md") -> "licenses/doc.md",
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
    paradoxTheme := Some(builtinParadoxTheme("generic"))
  )

lazy val `jsoniter-scala` = (project in file("jsoniter-scala"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.`jsoniter-scala`,
    name := "structible-jsoniter-scala",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val quill = (project in file("quill"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.quill,
    name := "structible-quill",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
