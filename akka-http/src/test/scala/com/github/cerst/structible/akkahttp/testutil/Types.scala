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

package com.github.cerst.structible.akkahttp.testutil

import akka.http.scaladsl.server.PathMatcher1
import akka.http.scaladsl.unmarshalling.Unmarshaller
import com.github.cerst.structible.akkahttp.{StructiblePathMatcher, StructibleUnmarshaller}
import com.github.cerst.structible.core.Structible

// ================================================================================================================
// DOUBLE
// ================================================================================================================
final case class NegDouble private (value: Double) extends AnyVal

object NegDouble {

  implicit val structibleForNegDouble: Structible[Double, NegDouble] = Structible.instanceUnsafe(apply, _.value)

  implicit val unmarshallerForNegDouble: Unmarshaller[String, NegDouble] = StructibleUnmarshaller()

  val pm: PathMatcher1[NegDouble] = StructiblePathMatcher.doubleNumber

  def apply(value: Double): NegDouble = {
    require(value < 0, "NegDouble.value must be > 0")
    new NegDouble(value)
  }

}

// ================================================================================================================
// INT
// ================================================================================================================
final case class OddInt private (value: Int) extends AnyVal

object OddInt {

  implicit val structibleForOddInt: Structible[Int, OddInt] = Structible.instanceUnsafe(apply, _.value)

  implicit val unmarshallerForOddInt: Unmarshaller[String, OddInt] = StructibleUnmarshaller()

  val hexIntPm: PathMatcher1[OddInt] = StructiblePathMatcher.hexIntNumber

  val intPm: PathMatcher1[OddInt] = StructiblePathMatcher.intNumber

  def apply(value: Int): OddInt = {
    require(value % 2 == 1, "OddInt.value must be odd")
    new OddInt(value)
  }

}

// ================================================================================================================
// LONG
// ================================================================================================================
final case class PosLong private (value: Long) extends AnyVal

object PosLong {

  implicit val structibleForNegLong: Structible[Long, PosLong] = Structible.instanceUnsafe(apply, _.value)

  implicit val unmarshallerForNegLong: Unmarshaller[String, PosLong] = StructibleUnmarshaller()

  val hexLongPm: PathMatcher1[PosLong] = StructiblePathMatcher.hexLongNumber

  val longPm: PathMatcher1[PosLong] = StructiblePathMatcher.longNumber

  def apply(value: Long): PosLong = {
    require(value > 0, "NegLong.value must be > 0")
    new PosLong(value)
  }

}

// ================================================================================================================
// STRING
// ================================================================================================================
final case class NonEmptyString private (value: String) extends AnyVal

object NonEmptyString {

  implicit val structibleForNonEmptyString: Structible[String, NonEmptyString] =
    Structible.instanceUnsafe(apply, _.value)

  implicit val unmarshallerForNonEmptyString: Unmarshaller[String, NonEmptyString] = StructibleUnmarshaller()

  val pm: PathMatcher1[NonEmptyString] = StructiblePathMatcher.segment

  def apply(value: String): NonEmptyString = {
    require(value.nonEmpty, "NonEmptyString.value must not be empty")
    new NonEmptyString(value)
  }
}
