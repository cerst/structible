import ModuleIDSyntax.toModuleIDSyntax
import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val Akka = "2.5.14"
    val AkkaHttp = "10.1.3"
    val Configs = "0.4.4"
    val JsoniterScala = "0.29.2"
    val Quill = "2.5.4"
    val UTest = "0.6.4"
  }

  // comment licenses for dependencies using the SPDX short identifier (see e.g. https://opensource.org/licenses/Apache-2.0)
  // rationale: double check the license when adding a new library avoid having to remove a problematic one later on when it is in use and thus hard to remove
  object Library {
    // Apache-2.0
    val AkkaStreamTestki = "com.typesafe.akka" %% "akka-stream-testkit" % Version.Akka
    // Apache-2.0
    val AkkaHttp = "com.typesafe.akka" %% "akka-http" % Version.AkkaHttp

    // Apache-2.0
    val Configs = "com.github.kxbmap" %% "configs" % Version.Configs
    // MIT
    val JsoniterScalaCore = "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % Version.JsoniterScala
    // MIT
    val JsoniterScalaMacros = "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % Version.JsoniterScala // required only in compile-time
    // Apache-2.0
    val QuillCore = "io.getquill" %% "quill-core" % Version.Quill
    // Apache-2.0
    val QuillJdbc = "io.getquill" %% "quill-jdbc" % Version.Quill
    // MIT
    val UTest = "com.lihaoyi" %% "utest" % Version.UTest
  }

  val akkaHttpLibraries: Seq[ModuleID] =
    Seq(
      Library.AkkaStreamTestki % Test,
      Library.AkkaHttp % (Provided, Test),
      Library.UTest
    )

  val configsLibraries: Seq[ModuleID] =
    Seq(Library.Configs % Provided, Library.UTest % Test)

  val coreLibraries: Seq[ModuleID] = Seq()

  val jsoniterScalaLibraries: Seq[ModuleID] = Seq(
    Library.JsoniterScalaCore % Provided,
    Library.JsoniterScalaMacros % Provided,
    Library.UTest % Test
  )

  val quillLibraries: Seq[ModuleID] =
    Seq(Library.QuillCore % Provided, Library.QuillJdbc % Test)
}
