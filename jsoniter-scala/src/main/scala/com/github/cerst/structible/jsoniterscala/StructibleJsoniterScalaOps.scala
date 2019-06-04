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

package com.github.cerst.structible.jsoniterscala

import java.time.{Duration, OffsetDateTime, ZonedDateTime}
import java.util.UUID

import com.github.cerst.structible.core.Structible
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonCodec, JsonReader, JsonWriter}

// Jsoniter-Scala does not provide an implicit structure to derive json codecs - so, the derivations have to defined explicitly

private object newJsonCodec {

  final def apply[C, R](rName: String,
                        structible: Structible[C, R],
                        readC: JsonReader => C,
                        writeVal: JsonWriter => C => Unit,
                        readKeyAsC: JsonReader => C,
                        writeKey: JsonWriter => C => Unit): JsonCodec[R] = {

    new JsonCodec[R] {
      override def decodeValue(in: JsonReader, default: R): R = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, s"expected $rName value or null")
        } else {
          in.rollbackToken()
          def c = readC(in)
          structible constructSafe c match {
            case Left(error) =>
              in decodeError error
            case Right(r) =>
              r
          }
        }
      }

      override def encodeValue(r: R, out: JsonWriter): Unit = {
        if (r != null) {
          def c = structible.destruct(r)
          writeVal(out)(c)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: R = null.asInstanceOf[R]

      override def decodeKey(in: JsonReader): R = {
        def c = readKeyAsC(in)
        structible constructSafe c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }

      override def encodeKey(r: R, out: JsonWriter): Unit = {
        def c = structible.destruct(r)
        writeKey(out)(c)
      }
    }
  }

}

// =====================================================================================================================
// BigDecimal
// =====================================================================================================================
final class StructibleJsoniterScalaBigDecimalOps[R](val structible: Structible[BigDecimal, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[BigDecimal, R](
    "BigDecimal",
    structible,
    readC = _.readBigDecimal(default = null),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsBigDecimal(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// BigInt
// =====================================================================================================================
final class StructibleJsoniterScalaBigIntOps[R](val structible: Structible[BigInt, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[BigInt, R](
    "BigInt",
    structible,
    readC = _.readBigInt(default = null),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsBigInt(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// BOOLEAN
// =====================================================================================================================
final class StructibleJsoniterScalaBooleanOps[R](val structible: Structible[Boolean, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] =
    newJsonCodec[Boolean, R](
      "Boolean",
      structible,
      readC = _.readBoolean,
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsBoolean(),
      writeKey = _.writeKey
    )

}

// =====================================================================================================================
// DOUBLE
// =====================================================================================================================
final class StructibleJsoniterScalaDoubleOps[R](val structible: Structible[Double, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[Double, R](
    "Double",
    structible,
    readC = _.readDouble(),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsDouble(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// DURATION
// =====================================================================================================================
final class StructibleJsoniterScalaDurationOps[R](val structible: Structible[Duration, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[Duration, R](
    "Duration",
    structible,
    readC = _.readDuration(default = null),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsDuration(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// FLOAT
// =====================================================================================================================
final class StructibleJsoniterScalaFloatOps[R](val structible: Structible[Float, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[Float, R](
    "Float",
    structible,
    readC = _.readFloat(),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsFloat(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// INT
// =====================================================================================================================
final class StructibleJsoniterScalaIntOps[R](val structible: Structible[Int, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[Int, R](
    "Int",
    structible,
    readC = _.readInt(),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsInt(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// LONG
// =====================================================================================================================
final class StructibleJsoniterScalaLongOps[R](val structible: Structible[Long, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[Long, R](
    "Long",
    structible,
    readC = _.readLong(),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsLong(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// OFFSET DATE TIME
// =====================================================================================================================
final class StructibleJsoniterScalaOffsetDateTimeOps[R](val structible: Structible[OffsetDateTime, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[OffsetDateTime, R](
    "OffsetDateTime",
    structible,
    readC = _.readOffsetDateTime(default = null),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsOffsetDateTime(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// SHORT
// =====================================================================================================================
final class StructibleJsoniterScalaShortOps[R](val structible: Structible[Short, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[Short, R](
    "Short",
    structible,
    readC = _.readShort(),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsShort(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// STRING
// =====================================================================================================================
final class StructibleJsoniterScalaStringOps[R](val structible: Structible[String, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[String, R](
    "String",
    structible,
    readC = _.readString(default = null),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsString(),
    writeKey = _.writeKey
  )

}


// =====================================================================================================================
// UUID
// =====================================================================================================================
final class StructibleJsoniterScalaUuidOps[R](val structible: Structible[UUID, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[UUID, R](
    "UUID",
    structible,
    readC = _.readUUID(default = null),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsUUID(),
    writeKey = _.writeKey
  )

}

// =====================================================================================================================
// ZONED DATE TIME
// =====================================================================================================================
final class StructibleJsoniterScalaZonedDateTimeOps[R](val structible: Structible[ZonedDateTime, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = newJsonCodec[ZonedDateTime, R](
    "ZonedDateTime",
    structible,
    readC = _.readZonedDateTime(default = null),
    writeVal = _.writeVal,
    readKeyAsC = _.readKeyAsZonedDateTime(),
    writeKey = _.writeKey
  )

}
