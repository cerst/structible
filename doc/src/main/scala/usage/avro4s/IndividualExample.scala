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

package usage.avro4s

// #example
import com.github.cerst.structible.avro4s.ops._
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._
import com.sksamuel.avro4s.{AvroSchema, Decoder, Encoder, SchemaFor}
import org.apache.avro.Schema

object IndividualExample {

  final class UserId private (val value: Long) extends AnyVal

  object UserId {

    private val structible: Structible[Long, UserId] = Structible.structible(new UserId(_), _.value, c >= 0)

    implicit val decoderForUserId: Decoder[UserId] = structible.toDecoder

    implicit val encoderForUserId: Encoder[UserId] = structible.toEncoder

    implicit val schemaForForUserId: SchemaFor[UserId] = structible.toSchemaFor

    def apply(value: Long): UserId = structible.construct(value)
  }

  final case class User(id: UserId)

  object User {
    val schema: Schema = AvroSchema[User]
  }

}
// #example
