lazy val root = (project in file("."))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    name := "structible-root",
    // this project is not supposed to be used externally, so don't publish
    skip in publish := true
  )

lazy val core = (project in file("core"))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.coreLibraries,
    name := "structible"
  )

lazy val doc = (project in file("doc"))
  .enablePlugins(GitBranchPrompt, GitVersioning, ParadoxPlugin)
  // all these settings are only relevant to the "doc" project which is why they are not defined in CommonSettingsPlugin.scala
  .settings(
    name := "structible-doc",
    // trigger dump-license-report in all other projects and rename the output
    // (paradox uses the first heading as link name in '@@@index' containers AND cannot handle variables in links)
    (mappings in Compile) in paradoxMarkdownToHtml ++= Seq(
      (core / dumpLicenseReport).value / ((core / licenseReportTitle).value + ".md") -> "licenses/core.md",
      dumpLicenseReport.value / (licenseReportTitle.value + ".md") -> "licenses/doc.md"
    ),
    // trigger test compilation in projects which contain snippets to be shown in the documentation
    // rationale: manage documentation source code where it can be compiled (e.g. <project>/src/test/paradox) but does not end up in published artifacts
    paradox in Compile := {
      val _ = (core / compile in Test).value
      (paradox in Compile).value
    },
    // properties to be accessible from within the documentation
    paradoxProperties ++= Map(
      "version" -> version.value
    ),
    paradoxTheme := Some(builtinParadoxTheme("generic")),
    // this project is not supposed to be used externally, so don't publish
    skip in publish := true
  )