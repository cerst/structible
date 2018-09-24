import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val Akka = "2.5.14"
    val AkkaHttp = "10.1.5"
    val Configs = "0.4.4"
    val JsoniterScala = "0.30.1"
    val Quill = "2.5.4"
    val UTest = "0.6.5"
  }

  // comment licenses for dependencies using the SPDX short identifier (see e.g. https://opensource.org/licenses/Apache-2.0)
  // rationale: double check the license when adding a new library avoid having to remove a problematic one later on when it is in use and thus hard to remove
  object Library {
    // Apache-2.0
    val AkkaHttp = "com.typesafe.akka" %% "akka-http" % Version.AkkaHttp
    // Apache-2.0
    val AkkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Version.AkkaHttp
    // Apache-2.0
    val Configs = "com.github.kxbmap" %% "configs" % Version.Configs
    // MIT
    val JsoniterScalaCore = "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % Version.JsoniterScala
    // MIT
    val JsoniterScalaMacros = "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % Version.JsoniterScala // required only at compile-time
    // Apache-2.0
    val QuillCore = "io.getquill" %% "quill-core" % Version.Quill
    // Apache-2.0
    val QuillJdbc = "io.getquill" %% "quill-jdbc" % Version.Quill
    // MIT
    val UTest = "com.lihaoyi" %% "utest" % Version.UTest
  }

  val `akka-http`: Seq[ModuleID] =
    Seq(Library.AkkaHttp % Provided, Library.AkkaHttpTestkit % Test, Library.UTest % Test)

  val configs: Seq[ModuleID] =
    Seq(Library.Configs % Provided, Library.UTest % Test)

  val core: Seq[ModuleID] = Seq()

  val `jsoniter-scala`: Seq[ModuleID] =
    Seq(Library.JsoniterScalaCore % Provided, Library.JsoniterScalaMacros % Provided, Library.UTest % Test)

  val quill: Seq[ModuleID] =
    Seq(Library.QuillCore % Provided, Library.QuillJdbc % Test)
}
