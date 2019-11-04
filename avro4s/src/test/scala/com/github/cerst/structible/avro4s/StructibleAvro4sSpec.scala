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

package com.github.cerst.structible.avro4s

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.github.cerst.structible.avro4s.ops._
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._
import com.sksamuel.avro4s._
import org.apache.avro.Schema
import org.scalatest.{FreeSpec, Matchers}

final class StructibleAvro4sSpec extends FreeSpec with Matchers {

  import StructibleAvro4sSpec._

  "schema is preserved when using toSchemaFor" in {
    assert(PersonId_1.schemaForForPersonId.schema(DefaultFieldMapper) == SchemaFor[Long].schema(DefaultFieldMapper))
  }

  "schema is preserved when using toBicoderWithSchemaFor" in {
    assert(
      PersonId_2.bicoderWithSchemaForForPersonId.schema(DefaultFieldMapper) == SchemaFor[Long]
        .schema(DefaultFieldMapper)
    )
  }

  "(de-)serialization matches common <-> refined when using toSchemaFor" in {
    val person_C = Person_1_C(1)
    val outputStream_C = new ByteArrayOutputStream()
    val avroOutputStream_C = AvroOutputStream.binary[Person_1_C].to(outputStream_C).build(Person_1_C.schema)
    avroOutputStream_C.write(person_C)
    avroOutputStream_C.flush()
    avroOutputStream_C.close()
    val bytes_C = outputStream_C.toByteArray

    val person_R = Person_1_R(PersonId_1(1))
    val outputStream_R = new ByteArrayOutputStream()
    val avroOutputStream_R = AvroOutputStream.binary[Person_1_R].to(outputStream_R).build(Person_1_R.schema)
    avroOutputStream_R.write(person_R)
    avroOutputStream_R.flush()
    avroOutputStream_R.close()
    val bytes_R = outputStream_R.toByteArray

    assert(bytes_C sameElements bytes_R)

    val inputStream = new ByteArrayInputStream(bytes_C)
    val avroInputStream = AvroInputStream.binary[Person_1_R].from(inputStream).build(Person_1_R.schema)
    val parsedPerson_R = avroInputStream.iterator.next()
    avroInputStream.close()

    assert(parsedPerson_R == person_R)
  }

  "(de-)serialization matches common <-> refined when using toBicoderWithSchemaFor" in {
    val person_C = Person_2_C(1)
    val outputStream_C = new ByteArrayOutputStream()
    val avroOutputStream_C = AvroOutputStream.binary[Person_2_C].to(outputStream_C).build(Person_2_C.schema)
    avroOutputStream_C.write(person_C)
    avroOutputStream_C.flush()
    avroOutputStream_C.close()
    val bytes_C = outputStream_C.toByteArray

    val person_R = Person_2_R(PersonId_2(1))
    val outputStream_R = new ByteArrayOutputStream()
    val avroOutputStream_R = AvroOutputStream.binary[Person_2_R].to(outputStream_R).build(Person_2_R.schema)
    avroOutputStream_R.write(person_R)
    avroOutputStream_R.flush()
    avroOutputStream_R.close()
    val bytes_R = outputStream_R.toByteArray

    assert(bytes_C sameElements bytes_R)

    val inputStream = new ByteArrayInputStream(bytes_C)
    val avroInputStream = AvroInputStream.binary[Person_2_R].from(inputStream).build(Person_2_R.schema)
    val parsedPerson_R = avroInputStream.iterator.next()
    avroInputStream.close()

    assert(parsedPerson_R == person_R)
  }

}

private object StructibleAvro4sSpec {

  final class PersonId_1(val value: Long) extends AnyVal

  object PersonId_1 {
    // you can also pass-in 'construct' functions returning Either[String, A], Option[A] or Try[A]
    private val structible: Structible[Long, PersonId_1] =
      Structible.structible(new PersonId_1(_), _.value, c >= 0)

    implicit val decoderForPersonId: Decoder[PersonId_1] = structible.toDecoder

    implicit val encoderForPersonId: Encoder[PersonId_1] = structible.toEncoder

    implicit val schemaForForPersonId: SchemaFor[PersonId_1] = structible.toSchemaFor

    def apply(value: Long): PersonId_1 = structible.construct(value)
  }

  final case class Person_1_C(id: Long)
  object Person_1_C {
    val schema: Schema = AvroSchema[Person_1_C]
  }

  final case class Person_1_R(id: PersonId_1)
  object Person_1_R {
    val schema: Schema = AvroSchema[Person_1_R]
  }

  final class PersonId_2(val value: Long) extends AnyVal
  object PersonId_2 {
    private val structible: Structible[Long, PersonId_2] =
      Structible.structible(new PersonId_2(_), _.value, c >= 0)

    implicit val bicoderWithSchemaForForPersonId: BicoderWithSchemaFor[PersonId_2] = structible.toBicoderWithSchemaFor

    def apply(value: Long): PersonId_2 = structible.construct(value)
  }

  final case class Person_2_C(personId: Long)
  object Person_2_C {
    val schema: Schema = AvroSchema[Person_2_C]
  }

  final case class Person_2_R(id: PersonId_2)
  object Person_2_R {
    val schema: Schema = AvroSchema[Person_2_R]
  }

}
