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

// =====================================================================================================================
// BigDecimal
// =====================================================================================================================
final class BigDecimalValueClass private (val value: BigDecimal) extends AnyVal

object BigDecimalValueClass {
  private val structible: Structible[BigDecimal, BigDecimalValueClass] =
    Structible.structible(new BigDecimalValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForBigDecimalValueClass: JsonCodec[BigDecimalValueClass] =
    structible.toJsonCodec(null.asInstanceOf[BigDecimalValueClass])

  def apply(value: BigDecimal): BigDecimalValueClass = structible.construct(value)

}

// =====================================================================================================================
// BigInt
// =====================================================================================================================
final class BigIntValueClass private (val value: BigInt) extends AnyVal

object BigIntValueClass {
  private val structible: Structible[BigInt, BigIntValueClass] =
    Structible.structible(new BigIntValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForBigIntValueClass: JsonCodec[BigIntValueClass] =
    structible.toJsonCodec(null.asInstanceOf[BigIntValueClass])

  def apply(value: BigInt): BigIntValueClass = structible.construct(value)
}

// =====================================================================================================================
// BOOLEAN
// =====================================================================================================================
final class BooleanValueClass private (val value: Boolean) extends AnyVal

object BooleanValueClass {
  private val structible: Structible[Boolean, BooleanValueClass] =
    Structible.structible(new BooleanValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForBooleanValueClass: JsonCodec[BooleanValueClass] =
    structible.toJsonCodec(null.asInstanceOf[BooleanValueClass])

  def apply(value: Boolean): BooleanValueClass = structible.construct(value)
}

// =====================================================================================================================
// DOUBLE
// =====================================================================================================================
final class DoubleValueClass private (val value: Double) extends AnyVal

object DoubleValueClass {
  private val structible: Structible[Double, DoubleValueClass] =
    Structible.structible(new DoubleValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForDoubleValueClass: JsonCodec[DoubleValueClass] =
    structible.toJsonCodec(null.asInstanceOf[DoubleValueClass])

  def apply(value: Double): DoubleValueClass = structible.construct(value)
}

// =====================================================================================================================
// DURATION
// =====================================================================================================================
final class DurationValueClass private (val value: Duration) extends AnyVal

object DurationValueClass {
  private val structible: Structible[Duration, DurationValueClass] =
    Structible.structible(new DurationValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForDurationValueClass: JsonCodec[DurationValueClass] =
    structible.toJsonCodec(null.asInstanceOf[DurationValueClass])

  def apply(value: Duration): DurationValueClass = structible.construct(value)
}

// =====================================================================================================================
// FLOAT
// =====================================================================================================================
final class FloatValueClass private (val value: Float) extends AnyVal

object FloatValueClass {
  private val structible: Structible[Float, FloatValueClass] =
    Structible.structible(new FloatValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForFloatValueClass: JsonCodec[FloatValueClass] =
    structible.toJsonCodec(null.asInstanceOf[FloatValueClass])

  def apply(value: Float): FloatValueClass = structible.construct(value)
}

// =====================================================================================================================
// INT
// =====================================================================================================================
final class IntValueClass private (val value: Int) extends AnyVal

object IntValueClass {
  private val structible: Structible[Int, IntValueClass] =
    Structible.structible(new IntValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForIntValueClass: JsonCodec[IntValueClass] =
    structible.toJsonCodec(null.asInstanceOf[IntValueClass])

  def apply(value: Int): IntValueClass = structible.construct(value)
}

// =====================================================================================================================
// LONG
// =====================================================================================================================
final class LongValueClass private (val value: Long) extends AnyVal

object LongValueClass {
  private val structible: Structible[Long, LongValueClass] =
    Structible.structible(new LongValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForLongValueClass: JsonCodec[LongValueClass] =
    structible.toJsonCodec(null.asInstanceOf[LongValueClass])

  def apply(value: Long): LongValueClass = structible.construct(value)
}

// =====================================================================================================================
// OFFSET DATE TIME
// =====================================================================================================================
final class OffsetDateTimeValueClass private (val value: OffsetDateTime) extends AnyVal

object OffsetDateTimeValueClass {
  private val structible: Structible[OffsetDateTime, OffsetDateTimeValueClass] =
    Structible.structible(new OffsetDateTimeValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForOffsetDateTimeValueClass: JsonCodec[OffsetDateTimeValueClass] =
    structible.toJsonCodec(null.asInstanceOf[OffsetDateTimeValueClass])

  def apply(value: OffsetDateTime): OffsetDateTimeValueClass = structible.construct(value)
}

// =====================================================================================================================
// SHORT
// =====================================================================================================================
final class ShortValueClass private (val value: Short) extends AnyVal

object ShortValueClass {
  private val structible: Structible[Short, ShortValueClass] =
    Structible.structible(new ShortValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForShortValueClass: JsonCodec[ShortValueClass] =
    structible.toJsonCodec(null.asInstanceOf[ShortValueClass])

  def apply(value: Short): ShortValueClass = structible.construct(value)
}

// =====================================================================================================================
// STRING
// =====================================================================================================================
final class StringValueClass private (val value: String) extends AnyVal

object StringValueClass {
  private val structible: Structible[String, StringValueClass] =
    Structible.structible(new StringValueClass(_), _.value, c.nonEmpty)

  implicit val jsonCodecForStringValueClass: JsonCodec[StringValueClass] =
    structible.toJsonCodec(null.asInstanceOf[StringValueClass])

  def apply(value: String): StringValueClass = structible.construct(value)
}

// =====================================================================================================================
// UUID
// =====================================================================================================================
final class UUIDValueClass private (val value: UUID) extends AnyVal

object UUIDValueClass {
  private val structible: Structible[UUID, UUIDValueClass] =
    Structible.structible(new UUIDValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForUUIDValueClass: JsonCodec[UUIDValueClass] =
    structible.toJsonCodec(null.asInstanceOf[UUIDValueClass])

  def apply(value: UUID): UUIDValueClass = structible.construct(value)
}

// =====================================================================================================================
// ZONED DATE TIME
// =====================================================================================================================
final class ZonedDateTimeValueClass(val value: ZonedDateTime) extends AnyVal

object ZonedDateTimeValueClass {
  private val structible: Structible[ZonedDateTime, ZonedDateTimeValueClass] =
    Structible.structible(new ZonedDateTimeValueClass(_), _.value, c.unconstrained)

  implicit val jsonCodecForZonedDateTimeValueClass: JsonCodec[ZonedDateTimeValueClass] =
    structible.toJsonCodec(null.asInstanceOf[ZonedDateTimeValueClass])

  def apply(value: ZonedDateTime): ZonedDateTimeValueClass = structible.construct(value)
}
