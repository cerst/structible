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

import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._
import com.github.cerst.structible.jsoniterscala.ops._
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._

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

  implicit val jsonValueCodecForWrapper: JsonValueCodec[Wrapper] = JsonCodecMaker.make[Wrapper](CodecMakerConfig())
}

// =====================================================================================================================
// BigDecimal
// =====================================================================================================================
final case class BigDecimalValueClass private (value: BigDecimal)

object BigDecimalValueClass {
  private val structible: Structible[BigDecimal, BigDecimalValueClass] =
    Structible.structible(new BigDecimalValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForBigDecimalValueClass: JsonCodec[BigDecimalValueClass] = structible.toJsonCodec

  def apply(value: BigDecimal): BigDecimalValueClass = structible.construct(value)

}

// =====================================================================================================================
// BigInt
// =====================================================================================================================
final case class BigIntValueClass private (value: BigInt)

object BigIntValueClass {
  private val structible: Structible[BigInt, BigIntValueClass] =
    Structible.structible(new BigIntValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForBigIntValueClass: JsonCodec[BigIntValueClass] = structible.toJsonCodec

  def apply(value: BigInt): BigIntValueClass = structible.construct(value)
}

// =====================================================================================================================
// BOOLEAN
// =====================================================================================================================
final case class BooleanValueClass private (value: Boolean)

object BooleanValueClass {
  private val structible: Structible[Boolean, BooleanValueClass] =
    Structible.structible(new BooleanValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForBooleanValueClass: JsonCodec[BooleanValueClass] = structible.toJsonCodec

  def apply(value: Boolean): BooleanValueClass = structible.construct(value)
}

// =====================================================================================================================
// DOUBLE
// =====================================================================================================================
final case class DoubleValueClass private (value: Double)

object DoubleValueClass {
  private val structible: Structible[Double, DoubleValueClass] =
    Structible.structible(new DoubleValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForDoubleValueClass: JsonCodec[DoubleValueClass] = structible.toJsonCodec

  def apply(value: Double): DoubleValueClass = structible.construct(value)
}

// =====================================================================================================================
// DURATION
// =====================================================================================================================
final case class DurationValueClass private (value: Duration)

object DurationValueClass {
  private val structible: Structible[Duration, DurationValueClass] =
    Structible.structible(new DurationValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForDurationValueClass: JsonCodec[DurationValueClass] = structible.toJsonCodec

  def apply(value: Duration): DurationValueClass = structible.construct(value)
}

// =====================================================================================================================
// FLOAT
// =====================================================================================================================
final case class FloatValueClass private (value: Float)

object FloatValueClass {
  private val structible: Structible[Float, FloatValueClass] =
    Structible.structible(new FloatValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForFloatValueClass: JsonCodec[FloatValueClass] = structible.toJsonCodec

  def apply(value: Float): FloatValueClass = structible.construct(value)
}

// =====================================================================================================================
// INT
// =====================================================================================================================
final case class IntValueClass private (value: Int)

object IntValueClass {
  private val structible: Structible[Int, IntValueClass] =
    Structible.structible(new IntValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForIntValueClass: JsonCodec[IntValueClass] = structible.toJsonCodec

  def apply(value: Int): IntValueClass = structible.construct(value)
}

// =====================================================================================================================
// LONG
// =====================================================================================================================
final case class LongValueClass private (value: Long)

object LongValueClass {
  private val structible: Structible[Long, LongValueClass] =
    Structible.structible(new LongValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForLongValueClass: JsonCodec[LongValueClass] = structible.toJsonCodec

  def apply(value: Long): LongValueClass = structible.construct(value)
}

// =====================================================================================================================
// OFFSET DATE TIME
// =====================================================================================================================
final case class OffsetDateTimeValueClass private (value: OffsetDateTime)

object OffsetDateTimeValueClass {
  private val structible: Structible[OffsetDateTime, OffsetDateTimeValueClass] =
    Structible.structible(new OffsetDateTimeValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForOffsetDateTimeValueClass: JsonCodec[OffsetDateTimeValueClass] = structible.toJsonCodec

  def apply(value: OffsetDateTime): OffsetDateTimeValueClass = structible.construct(value)
}

// =====================================================================================================================
// SHORT
// =====================================================================================================================
final case class ShortValueClass private (value: Short)

object ShortValueClass {
  private val structible: Structible[Short, ShortValueClass] =
    Structible.structible(new ShortValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForShortValueClass: JsonCodec[ShortValueClass] = structible.toJsonCodec

  def apply(value: Short): ShortValueClass = structible.construct(value)
}

// =====================================================================================================================
// STRING
// =====================================================================================================================
final case class StringValueClass private (value: String)

object StringValueClass {
  private val structible: Structible[String, StringValueClass] =
    Structible.structible(new StringValueClass(_), _.value, c.nonEmpty, hideC = false)

  implicit val jsonCodecForStringValueClass: JsonCodec[StringValueClass] = structible.toJsonCodec

  def apply(value: String): StringValueClass = structible.construct(value)
}

// =====================================================================================================================
// UUID
// =====================================================================================================================
final case class UUIDValueClass private (value: UUID)

object UUIDValueClass {
  private val structible: Structible[UUID, UUIDValueClass] =
    Structible.structible(new UUIDValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForUUIDValueClass: JsonCodec[UUIDValueClass] = structible.toJsonCodec

  def apply(value: UUID): UUIDValueClass = structible.construct(value)
}

// =====================================================================================================================
// ZONED DATE TIME
// =====================================================================================================================
final case class ZonedDateTimeValueClass(value: ZonedDateTime)

object ZonedDateTimeValueClass {
  private val structible: Structible[ZonedDateTime, ZonedDateTimeValueClass] =
    Structible.structible(new ZonedDateTimeValueClass(_), _.value, c.unconstrained, hideC = false)

  implicit val jsonCodecForZonedDateTimeValueClass: JsonCodec[ZonedDateTimeValueClass] = structible.toJsonCodec

  def apply(value: ZonedDateTime): ZonedDateTimeValueClass = structible.construct(value)
}
