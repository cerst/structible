import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val Akka = "2.5.25"
    val AkkaHttp = "10.1.9"
    val Avro4s = "3.0.1"
    val Configs = "0.4.4"
    val JsoniterScala = "0.55.4"
    val Pureconfig = "0.12.0"
    val Quill = "3.4.4" // 3.4.6 not on Maven as of 2019-09-17
    val Silencer = "1.4.1"
    val UTest = "0.7.1"
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
    val Avro4sCore = "com.sksamuel.avro4s" %% "avro4s-core" % Version.Avro4s
    // Apache-2.0
    val Configs = "com.github.kxbmap" %% "configs" % Version.Configs
    // MIT
    val JsoniterScalaCore = "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % Version.JsoniterScala
    // MIT
    val JsoniterScalaMacros = "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % Version.JsoniterScala // required only at compile-time
    // MPL-2.0
    val Pureconfig = "com.github.pureconfig" %% "pureconfig" % Version.Pureconfig
    // Apache-2.0
    val QuillCore = "io.getquill" %% "quill-core" % Version.Quill
    // Apache-2.0
    val QuillJdbc = "io.getquill" %% "quill-jdbc" % Version.Quill
    val SilencerCompilerPlugin = compilerPlugin("com.github.ghik" %% "silencer-plugin" % Version.Silencer)
    val SilencerLib = "com.github.ghik" %% "silencer-lib" % Version.Silencer
    // MIT
    val UTest = "com.lihaoyi" %% "utest" % Version.UTest
  }

  val `akka-http`: Seq[ModuleID] = Seq(
    Library.AkkaHttp % Provided,
    Library.AkkaHttpTestkit % Test,
    Library.AkkaStreamTeskit % Test,
    Library.UTest % Test
  )

  val avro4s: Seq[ModuleID] = Seq(Library.Avro4sCore, Library.UTest % Test)

  val configs: Seq[ModuleID] =
    Seq(Library.Configs % Provided, Library.UTest % Test)

  val core: Seq[ModuleID] = Seq(
    Library.SilencerCompilerPlugin,
    Library.SilencerLib % Provided
  )

  val doc: Seq[ModuleID] = Seq(
    Library.AkkaHttp % Provided,
    Library.AkkaStream % Provided,
    Library.Configs % Provided,
    Library.JsoniterScalaCore % Provided,
    Library.JsoniterScalaMacros % Provided,
    Library.QuillJdbc % Provided
  )

  val `jsoniter-scala`: Seq[ModuleID] =
    Seq(Library.JsoniterScalaCore, Library.JsoniterScalaMacros % Provided, Library.UTest % Test)

  val pureconfig: Seq[ModuleID] = Seq(Library.Pureconfig, Library.UTest % Test)

  val quill: Seq[ModuleID] =
    Seq(Library.QuillCore % Provided, Library.QuillJdbc % Test)

}
