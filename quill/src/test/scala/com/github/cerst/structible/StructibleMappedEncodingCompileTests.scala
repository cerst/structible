package com.github.cerst.structible
import com.github.cerst.structible.quill.StructibleMappedEncoding
import io.getquill._

object StructibleMappedEncodingCompileTests {

  object TestContext
      extends MysqlJdbcContext(SnakeCase, "configPrefix")
      with StructibleMappedEncoding

  import TestContext._

  final case class PersonId(value: Int) {
    require(value >= 0, "PersonId must be >= 0")
  }

  object PersonId {
    implicit val structibleForPersonId: Structible[Int, PersonId] =
      Structible.instanceUnsafe(apply, _.value)
  }

  final case class Person(id: PersonId, name: String)

  def findById(personId: PersonId) = {
    run(quote {
      query[Person]
        .filter(_.id == lift(personId))
    })
  }

  // for this to work, implicit en

}
