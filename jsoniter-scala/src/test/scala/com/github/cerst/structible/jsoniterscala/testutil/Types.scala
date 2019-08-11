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

package com.github.cerst.structible.jsoniterscala.testutil

import java.time.{Duration, OffsetDateTime, ZonedDateTime}
import java.util.UUID

import com.github.cerst.structible.core.Structible
import com.github.cerst.structible.jsoniterscala.ops._
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonCodec, JsonValueCodec}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}

final case class Wrapper(bigDecimalValueClass: BigDecimalValueClass,
                         bigIntValueClass: BigIntValueClass,
                         booleanValueClass: BooleanValueClass,
                         doubleValueClass: DoubleValueClass,
                         durationValueClass: DurationValueClass,
                         floatValueClass: FloatValueClass,
                         intValueClass: IntValueClass,
                         longValueClass: LongValueClass,
                         offsetDateTimeValueClass: OffsetDateTimeValueClass,
                         shortValueClass: ShortValueClass,
                         stringValueClass: StringValueClass,
                         uuidValueClass: UUIDValueClass,
                         zonedDateTimeValueClass: ZonedDateTimeValueClass)

object Wrapper {

  implicit val jsonValueCodecForWrapper: JsonValueCodec[Wrapper] =
    JsonCodecMaker.make[Wrapper](CodecMakerConfig())
}

// =====================================================================================================================
// BigDecimal
// =====================================================================================================================
final case class BigDecimalValueClass(value: BigDecimal)

object BigDecimalValueClass {
  private val structible: Structible[BigDecimal, BigDecimalValueClass] =
    Structible.structible(BigDecimalValueClass.apply, _.value)

  implicit val jsonCodecForBigDecimalValueClass: JsonCodec[BigDecimalValueClass] =
    BigDecimalValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// BigInt
// =====================================================================================================================
final case class BigIntValueClass(value: BigInt)

object BigIntValueClass {
  private val structible: Structible[BigInt, BigIntValueClass] =
    Structible.structible(BigIntValueClass.apply, _.value)

  implicit val jsonCodecForBigIntValueClass: JsonCodec[BigIntValueClass] =
    BigIntValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// BOOLEAN
// =====================================================================================================================
final case class BooleanValueClass(value: Boolean)

object BooleanValueClass {
  private val structible: Structible[Boolean, BooleanValueClass] =
    Structible.structible(BooleanValueClass.apply, _.value)

  implicit val jsonCodecForBooleanValueClass: JsonCodec[BooleanValueClass] =
    BooleanValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// DOUBLE
// =====================================================================================================================
final case class DoubleValueClass(value: Double)

object DoubleValueClass {
  private val structible: Structible[Double, DoubleValueClass] =
    Structible.structible(DoubleValueClass.apply, _.value)

  implicit val jsonCodecForDoubleValueClass: JsonCodec[DoubleValueClass] =
    DoubleValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// DURATION
// =====================================================================================================================
final case class DurationValueClass(value: Duration)

object DurationValueClass {
  private val structible: Structible[Duration, DurationValueClass] =
    Structible.structible(DurationValueClass.apply, _.value)

  implicit val jsonCodecForDurationValueClass: JsonCodec[DurationValueClass] =
    DurationValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// FLOAT
// =====================================================================================================================
final case class FloatValueClass(value: Float)

object FloatValueClass {
  private val structible: Structible[Float, FloatValueClass] =
    Structible.structible(FloatValueClass.apply, _.value)

  implicit val jsonCodecForFloatValueClass: JsonCodec[FloatValueClass] =
    FloatValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// INT
// =====================================================================================================================
final case class IntValueClass(value: Int)

object IntValueClass {
  private val structible: Structible[Int, IntValueClass] =
    Structible.structible(IntValueClass.apply, _.value)

  implicit val jsonCodecForIntValueClass: JsonCodec[IntValueClass] =
    IntValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// LONG
// =====================================================================================================================
final case class LongValueClass(value: Long)

object LongValueClass {
  private val structible: Structible[Long, LongValueClass] =
    Structible.structible(LongValueClass.apply, _.value)

  implicit val jsonCodecForLongValueClass: JsonCodec[LongValueClass] =
    LongValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// OFFSET DATE TIME
// =====================================================================================================================
final case class OffsetDateTimeValueClass(value: OffsetDateTime)

object OffsetDateTimeValueClass {
  private val structible: Structible[OffsetDateTime, OffsetDateTimeValueClass] =
    Structible.structible(OffsetDateTimeValueClass.apply, _.value)

  implicit val jsonCodecForOffsetDateTimeValueClass: JsonCodec[OffsetDateTimeValueClass] =
    OffsetDateTimeValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// SHORT
// =====================================================================================================================
final case class ShortValueClass(value: Short)

object ShortValueClass {
  private val structible: Structible[Short, ShortValueClass] =
    Structible.structible(ShortValueClass.apply, _.value)

  implicit val jsonCodecForShortValueClass: JsonCodec[ShortValueClass] =
    ShortValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// STRING
// =====================================================================================================================
final case class StringValueClass(value: String)

object StringValueClass {
  private val structible: Structible[String, StringValueClass] =
    Structible.structible(StringValueClass.apply, _.value)

  implicit val jsonCodecForStringValueClass: JsonCodec[StringValueClass] =
    StringValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// UUID
// =====================================================================================================================
final case class UUIDValueClass(value: UUID)

object UUIDValueClass {
  private val structible: Structible[UUID, UUIDValueClass] =
    Structible.structible(UUIDValueClass.apply, _.value)

  implicit val jsonCodecForUUIDValueClass: JsonCodec[UUIDValueClass] =
    UUIDValueClass.structible.toJsonCodec
}

// =====================================================================================================================
// ZONED DATE TIME
// =====================================================================================================================
final case class ZonedDateTimeValueClass(value: ZonedDateTime)

object ZonedDateTimeValueClass {
  private val structible: Structible[ZonedDateTime, ZonedDateTimeValueClass] =
    Structible.structible(ZonedDateTimeValueClass.apply, _.value)

  implicit val jsonCodecForZonedDateTimeValueClass: JsonCodec[ZonedDateTimeValueClass] =
    ZonedDateTimeValueClass.structible.toJsonCodec
}
