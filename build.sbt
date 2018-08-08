lazy val root = (project in file("."))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    name := "structible-root",
    // this project is not supposed to be used externally, so don't publish
    skip in publish := true
  )

lazy val `akka-http` = (project in file("akka-http"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.`akka-http`,
    name := "structible-akka-http"
  )

lazy val configs = (project in file("configs"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.configs,
    name := "structible-configs"
  )

lazy val core = (project in file("core"))
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.core,
    name := "structible-core",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val doc = (project in file("doc"))
  .enablePlugins(GitBranchPrompt, GitVersioning, ParadoxPlugin)
  // all these settings are only relevant to the "doc" project which is why they are not defined in CommonSettingsPlugin.scala
  .settings(
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

lazy val `jsoniter-scala` = (project in file("jsoniter-scala"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.`jsoniter-scala`,
    name := "structible-jsoniter-scala"
  )

lazy val quill = (project in file("quill"))
  .dependsOn(core)
  .enablePlugins(GitBranchPrompt, GitVersioning)
  .settings(
    libraryDependencies ++= Dependencies.quill,
    name := "structible-quill"
  )
