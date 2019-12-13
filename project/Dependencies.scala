import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val Akka = "2.5.26"
    val AkkaHttp = "10.1.10"
    val Avro4s = "3.0.3"
    val Configs = "0.4.4"
    val JsoniterScala = "1.2.0"
    val Pureconfig = "0.12.1"
    val Quill = "3.4.10"
    val ScalaCheck = "1.14.2"
    val Scalatest = "3.0.8"
    val Silencer = "1.4.2"
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
    // always only used for compilation
    val JsoniterScalaMacros = "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % Version.JsoniterScala % Provided
    // MPL-2.0
    val Pureconfig = "com.github.pureconfig" %% "pureconfig" % Version.Pureconfig
    // Apache-2.0
    val QuillCore = "io.getquill" %% "quill-core" % Version.Quill
    // Apache-2.0
    val QuillJdbc = "io.getquill" %% "quill-jdbc" % Version.Quill
    // BSD-3-Clause
    val ScalaCheck = "org.scalacheck" %% "scalacheck" % Version.ScalaCheck
    // Apache-2.0
    val Scalatest = "org.scalatest" %% "scalatest" % Version.Scalatest
    // Apache-2.0
    val SilencerCompilerPlugin = compilerPlugin("com.github.ghik" %% "silencer-plugin" % Version.Silencer)
    // Apache-2.0
    // always only used for compilation
    val SilencerLib = "com.github.ghik" %% "silencer-lib" % Version.Silencer % Provided
  }

  val `akka-http`: Seq[ModuleID] =
    Seq(Library.AkkaHttp, Library.AkkaHttpTestkit % Test, Library.AkkaStreamTeskit % Test, Library.Scalatest % Test)

  val avro4s: Seq[ModuleID] = Seq(Library.Avro4sCore, Library.Scalatest % Test)

  val configs: Seq[ModuleID] = Seq(Library.Configs, Library.Scalatest % Test)

  val core: Seq[ModuleID] =
    Seq(Library.SilencerCompilerPlugin, Library.SilencerLib, Library.Scalatest % Test, Library.ScalaCheck % Test)

  val doc: Seq[ModuleID] = Seq(
    Library.AkkaStream,
    Library.JsoniterScalaMacros,
    Library.QuillJdbc
  )

  val `jsoniter-scala`: Seq[ModuleID] =
    Seq(Library.JsoniterScalaCore, Library.JsoniterScalaMacros, Library.Scalatest % Test)

  val pureconfig: Seq[ModuleID] = Seq(Library.Pureconfig, Library.Scalatest % Test)

  val quill: Seq[ModuleID] =
    Seq(Library.QuillCore, Library.QuillJdbc % Test)

}
