import sbt._

object Dependencies {

  val resolvers: Seq[Resolver] = Seq()

  object Version {
    val Enumeratum = "1.5.13"
    val JsoniterScala = "0.29.2"
    val Quill = "2.5.4"
    val UTest = "0.6.4"
  }

  // comment licenses for dependencies using the SPDX short identifier (see e.g. https://opensource.org/licenses/Apache-2.0)
  // rationale: double check the license when adding a new library avoid having to remove a problematic one later on when it is in use and thus hard to remove
  object Library {
    // MIT
    val Enumeratum = "com.beachape" %% "enumeratum" % Version.Enumeratum
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

  val coreLibraries: Seq[ModuleID] = Seq()

  val jsoniterScalaLibraries: Seq[ModuleID] = Seq(
    Library.Enumeratum % Test,
    Library.JsoniterScalaCore % Provided,
    Library.JsoniterScalaMacros % Provided,
    Library.UTest % Test
  )

  val quillLibraries: Seq[ModuleID] =
    Seq(Library.QuillCore % Provided, Library.QuillJdbc % Test)
}
