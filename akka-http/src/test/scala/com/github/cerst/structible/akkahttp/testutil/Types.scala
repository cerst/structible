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
import com.github.cerst.structible.akkahttp.ops._
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._

// ================================================================================================================
// DOUBLE
// ================================================================================================================
final case class NegDouble private (value: Double) extends AnyVal

object NegDouble {

  private val structible: Structible[Double, NegDouble] =
    Structible.structible(new NegDouble(_), _.value, c < 0, hideC = false)

  implicit val unmarshallerForNegDouble: Unmarshaller[String, NegDouble] = structible.toUnmarshaller

  val pm: PathMatcher1[NegDouble] = structible.toPathMatcher

  def apply(value: Double): NegDouble = structible.construct(value)

}

// ================================================================================================================
// INT
// ================================================================================================================
final case class NonNegInt private (value: Int) extends AnyVal

object NonNegInt {

  private val structible: Structible[Int, NonNegInt] =
    Structible.structible(new NonNegInt(_), _.value, c >= 0, hideC = false)

  implicit val unmarshallerForOddInt: Unmarshaller[String, NonNegInt] = structible.toUnmarshaller

  val hexIntPm: PathMatcher1[NonNegInt] = structible.toHexPathMatcher

  val intPm: PathMatcher1[NonNegInt] = structible.toPathMatcher

  def apply(value: Int): NonNegInt = structible.construct(value)

}

// ================================================================================================================
// LONG
// ================================================================================================================
final case class PosLong private (value: Long) extends AnyVal

object PosLong {

  private val structible: Structible[Long, PosLong] =
    Structible.structible(new PosLong(_), _.value, c > 0, hideC = false)

  implicit val unmarshallerForNegLong: Unmarshaller[String, PosLong] = structible.toUnmarshaller

  val hexLongPm: PathMatcher1[PosLong] = structible.toHexPathMatcher

  val longPm: PathMatcher1[PosLong] = structible.toPathMatcher

  def apply(value: Long): PosLong = structible.construct(value)

}

// ================================================================================================================
// STRING
// ================================================================================================================
final case class NonEmptyString private (value: String) extends AnyVal

object NonEmptyString {

  private val structible: Structible[String, NonEmptyString] =
    Structible.structible(new NonEmptyString(_), _.value, c.nonEmpty, hideC = false)

  implicit val unmarshallerForNonEmptyString: Unmarshaller[String, NonEmptyString] = structible.toUnmarshaller

  val pm: PathMatcher1[NonEmptyString] = structible.toPathMatcher

  def apply(value: String): NonEmptyString = structible.construct(value)
}
