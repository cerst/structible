package com.github.cerst.structible.avro4s

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.github.cerst.structible.avro4s.ops._
import com.github.cerst.structible.core.Structible
import com.sksamuel.avro4s._
import org.apache.avro.Schema
import utest._

object StructibleAvro4sTests extends TestSuite {

  final case class PersonId_1(value: Long) {
    require(value >= 0, s"PersonId must be non-negative (got: '$value')")
  }
  object PersonId_1 {
    // you can also pass-in 'construct' functions returning Either[String, A], Option[A] or Try[A]
    private val structible: Structible[Long, PersonId_1] = Structible.structible(PersonId_1.apply, _.value)

    implicit val decoderForPersonId: Decoder[PersonId_1] = structible.toDecoder

    implicit val encoderForPersonId: Encoder[PersonId_1] = structible.toEncoder

    implicit val schemaForForPersonId: SchemaFor[PersonId_1] = structible.toSchemaFor
  }

  final case class Person_1_C(id: Long)
  object Person_1_C {
    val schema: Schema = AvroSchema[Person_1_C]
  }

  final case class Person_1_R(id: PersonId_1)
  object Person_1_R {
    val schema: Schema = AvroSchema[Person_1_R]
  }

  final case class PersonId_2(value: Long) {
    require(value >= 0, s"PersonId must be non-negative (got: '$value')")
  }
  object PersonId_2 {
    private val structible: Structible[Long, PersonId_2] = Structible.structible(PersonId_2.apply, _.value)
    implicit val bicoderWithSchemaForForPersonId: BicoderWithSchemaFor[PersonId_2] = structible.toBicoderWithSchemaFor
  }

  final case class Person_2_C(personId: Long)
  object Person_2_C {
    val schema: Schema = AvroSchema[Person_2_C]
  }

  final case class Person_2_R(id: PersonId_2)
  object Person_2_R {
    val schema: Schema = AvroSchema[Person_2_R]
  }

  override val tests: Tests = Tests {

    test("schema is preserved when using toSchemaFor") {
      assert(PersonId_1.schemaForForPersonId.schema(DefaultFieldMapper) == SchemaFor[Long].schema(DefaultFieldMapper))
    }

    test("schema is preserved when using toBicoderWithSchemaFor") {
      assert(
        PersonId_2.bicoderWithSchemaForForPersonId.schema(DefaultFieldMapper) == SchemaFor[Long]
          .schema(DefaultFieldMapper)
      )
    }

    test("(de-)serialization matches common <-> refined when using toSchemaFor") {
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

    test("(de-)serialization matches common <-> refined when using toBicoderWithSchemaFor") {
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
}
