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

package usage.jsoniterscala

// #example
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._
import com.github.cerst.structible.jsoniterscala.ops._
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._

object JsonCodecExample {

  final class UserId private (val value: Long) extends AnyVal

  object UserId {

    private val structible: Structible[Long, UserId] = Structible.structible(new UserId(_), _.value, c >= 0)

    // jsoniter-scala uses a null value internally which you have to provide explicitly when using AnyVal due to a compiler bug:
    // https://github.com/scala/bug/issues/8097
    // otherwise, the created codec throws a NullPointerException at runtime
    implicit val jsonCodecForUserId: JsonCodec[UserId] = structible.toJsonCodec(null.asInstanceOf[UserId])
  }

  final case class User(userId: UserId)

  object User {
    implicit val jsonCodecForPerson: JsonValueCodec[User] = JsonCodecMaker.make(CodecMakerConfig())
  }

}
// #example
