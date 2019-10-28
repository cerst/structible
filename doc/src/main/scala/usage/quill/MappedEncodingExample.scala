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

package usage.quill

// #example
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._
import com.github.cerst.structible.quill.ops._
import io.getquill.{MappedEncoding, PostgresJdbcContext, SnakeCase}

object MappedEncodingExample {

  final case class UserId private (value: Long) extends AnyVal

  object UserId {

    // you can also pass-in 'construct' functions returning Either[String, A], Option[A] or Try[A]
    private val structible: Structible[Long, UserId] =
      Structible.structible(UserId.apply, _.value, c >= 0, hideC = false)

    implicit val decodeForUserId: MappedEncoding[Long, UserId] = structible.toDecode

    implicit val encodeForUserId: MappedEncoding[UserId, Long] = structible.toEncode

  }

  final case class User(userId: UserId)

  // this specific context is solely for demonstration - any works
  object TestContext extends PostgresJdbcContext(SnakeCase, "configPrefix")

  import TestContext._

  def findUserById(userId: UserId): List[User] = {
    run(quote {
      query[User]
        .filter(_.userId == lift(userId))
    })
  }

}
// #example
