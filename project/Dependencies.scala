import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val Akka = "2.5.23"
    val AkkaHttp = "10.1.8"
    val Configs = "0.4.4"
    val JsoniterScala = "0.50.0"
    val Quill = "3.2.2"
    val UTest = "0.6.9"
  }

  // comment licenses for dependencies using the SPDX short identifier (see e.g. https://opensource.org/licenses/Apache-2.0)
  // rationale: double check the license when adding a new library avoid having to remove a problematic one later on when it is in use and thus hard to remove
  object Library {
    // Apache-2.0
    val AkkaHttp = "com.typesafe.akka" %% "akka-http" % Version.AkkaHttp
    // Apache-2.0
    val AkkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Version.AkkaHttp
    // Apache-2.0
    val AkkaStream = "com.typesafe.akka" %% "akka-stream" % Version.Akka
    // Apache-2.0
    val AkkaStreamTeskit = "com.typesafe.akka" %% "akka-stream-testkit" % Version.Akka
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

  val `akka-http`: Seq[ModuleID] = Seq(
    Library.AkkaHttp % Provided,
    Library.AkkaHttpTestkit % Test,
    Library.AkkaStreamTeskit % Test,
    Library.UTest % Test
  )

  val configs: Seq[ModuleID] =
    Seq(Library.Configs % Provided, Library.UTest % Test)

  val core: Seq[ModuleID] = Seq()

  val doc: Seq[ModuleID] = Seq(
    Library.AkkaHttp % Provided,
    Library.AkkaStream % Provided,
    Library.Configs % Provided,
    Library.JsoniterScalaCore % Provided,
    Library.JsoniterScalaMacros % Provided,
    Library.QuillJdbc % Provided
  )

  val `jsoniter-scala`: Seq[ModuleID] =
    Seq(Library.JsoniterScalaCore % Provided, Library.JsoniterScalaMacros % Provided, Library.UTest % Test)

  val quill: Seq[ModuleID] =
    Seq(Library.QuillCore % Provided, Library.QuillJdbc % Test)

}
