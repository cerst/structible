/*
 * Copyright (c) 2018 Constantin Gerstberger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
