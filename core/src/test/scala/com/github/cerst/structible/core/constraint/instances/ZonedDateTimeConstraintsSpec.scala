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

package com.github.cerst.structible.core.constraint.instances

import java.time.{Instant, OffsetDateTime, ZoneId, ZonedDateTime}

import com.github.cerst.structible.core.constraint.instances.ZonedDateTimeConstraints._
import com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax
import com.github.cerst.structible.core.testutil.NoShrink
import com.github.ghik.silencer.silent
import org.scalacheck.Gen
import org.scalatest.{FreeSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

final class ZonedDateTimeConstraintsSpec
    extends FreeSpec
    with Matchers
    with ScalaCheckDrivenPropertyChecks
    with NoShrink {

  import ZonedDateTimeConstraintsSpec._

  // ===================================================================================================================
  // <
  // ===================================================================================================================
  "'_ < x' does not succeed with y >= x" in {
    forAll(genBaseWithGreaterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.<(x).apply(y)
        val expected = List(s"_ < $x")

        assert(actual == expected)
    }
  }

  "'_ < x' succeeds with y < x" in {
    forAll(genBaseWithLesser) {
      case (x, y) =>
        val actual = ConstraintSyntax.<(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // <=
  // ===================================================================================================================
  "'_ <= x' does not succeed with y > x" in {
    forAll(genBaseWithGreater) {
      case (x, y) =>
        val actual = ConstraintSyntax.<=(x).apply(y)
        val expected = List(s"_ <= $x")

        assert(actual == expected)
    }
  }

  "'_ <= x' succeeds with y <= x" in {
    forAll(genBaseWithLesserOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.<=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // >
  // ===================================================================================================================
  "'_ > x' does not succeed with y <= x" in {
    forAll(genBaseWithLesserOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.>(x).apply(y)
        val expected = List(s"_ > $x")

        assert(actual == expected)
    }
  }

  "'_ > x' succeeds with y > x" in {
    forAll(genBaseWithGreater) {
      case (x, y) =>
        val actual = ConstraintSyntax.>(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // >=
  // ===================================================================================================================
  "'_ >= x' does not succeed with y < x" in {
    forAll(genBaseWithLesser) {
      case (x, y) =>
        val actual = ConstraintSyntax.>=(x).apply(y)
        val expected = List(s"_ >= $x")

        assert(actual == expected)
    }
  }

  "'_ >= x' succeeds with y >= x" in {
    forAll(genBaseWithGreaterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.>=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

}

object ZonedDateTimeConstraintsSpec {

  // don't know a better way to determine to exact MIN/ MAX based on ZoneIds (e.g. as done with OffsetDateTime)
  // 2 days less at each extreme hopefully is good enough (1 is no enough)
  private final val MinSeconds = OffsetDateTime.MIN.toEpochSecond + 60 * 60 * 24 * 2
  private final val MaxSeconds = OffsetDateTime.MAX.toEpochSecond - 60 * 60 * 24 * 2

  /** Max nanos of a second: 999.999.999 */
  private final val MaxNanos: Long = 999999999L

  // TODO: switch to scala.jdk.CollectionConverters after having dropped support for Scala 2.12
  @silent
  private final val genZoneId: Gen[ZoneId] = {
    import scala.collection.JavaConverters._
    Gen.oneOf(ZoneId.getAvailableZoneIds.asScala.toSeq).map(ZoneId.of)
  }

  private final val genBaseWithLesser: Gen[(ZonedDateTime, ZonedDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   make sure that there is at least one 'y | y < x', i,e. Gen(x).min == 1 AND
      //   generate y accordingly, i.e. Gen(y).max == xNanos -1
      // else
      //   both nanos do not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(if (xSeconds == ySeconds) 1L else 0L, MaxNanos)
      yNanos <- Gen.chooseNum(0L, if (xSeconds == ySeconds) xNanos - 1 else MaxNanos)
      xZoneId <- genZoneId
      yZoneId <- genZoneId
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = ZonedDateTime.ofInstant(xInstant, xZoneId)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = ZonedDateTime.ofInstant(yInstant, yZoneId)
      (x, y)
    }
  }

  private final val genBaseWithLesserOrEqual: Gen[(ZonedDateTime, ZonedDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y <= x', i,e. Gen(y).max == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0L, MaxNanos)
      yNanos <- Gen.chooseNum(0L, if (xSeconds == ySeconds) xNanos else MaxNanos)
      xZoneId <- genZoneId
      yZoneId <- genZoneId
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = ZonedDateTime.ofInstant(xInstant, xZoneId)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = ZonedDateTime.ofInstant(yInstant, yZoneId)
      (x, y)
    }
  }

  private final val genBaseWithGreater: Gen[(ZonedDateTime, ZonedDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   make sure that there is at least one 'y | y > x', i,e. Gen(x).max == MaxNanos -1 AND
      //   generate y accordingly, i.e. Gen(y).min == xNanos -1
      // else
      //   both nanos do not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0L, if (xSeconds == ySeconds) MaxNanos - 1 else MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos + 1 else 0, MaxNanos)
      xZoneId <- genZoneId
      yZoneId <- genZoneId
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = ZonedDateTime.ofInstant(xInstant, xZoneId)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = ZonedDateTime.ofInstant(yInstant, yZoneId)
      (x, y)
    }
  }

  private final val genBaseWithGreaterOrEqual: Gen[(ZonedDateTime, ZonedDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y >= x', i,e. Gen(y).min == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0L, MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos else 0, MaxNanos)
      xZoneId <- genZoneId
      yZoneId <- genZoneId
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = ZonedDateTime.ofInstant(xInstant, xZoneId)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = ZonedDateTime.ofInstant(yInstant, yZoneId)
      (x, y)
    }
  }

}
