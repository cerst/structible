/*
 * Copyright (c) 2019 Constantin Gerstberger
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

import java.time.{Instant, OffsetDateTime, ZoneOffset}

import com.github.cerst.structible.core.constraint.instances.OffsetDateTimeConstraints._
import com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax
import com.github.cerst.structible.core.testutil.NoShrink
import org.scalacheck.Gen
import org.scalatest.{FreeSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

final class OffsetDateTimeConstraintsSpec
    extends FreeSpec
    with Matchers
    with ScalaCheckDrivenPropertyChecks
    with NoShrink {

  import OffsetDateTimeConstraintsSpec._

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

object OffsetDateTimeConstraintsSpec {

  // min, max is not consistent between Instant and OffsetDateTime so we cannot re-use instant generators:
  // Instant.MIN                  = -1000000000-01-01T00:00:00Z
  // OffsetDateTime.MIN.toInstant = -1000000000-12-31T06:00:00Z
  // -
  // Instant.MAX                  = +1000000000-12-31T23:59:59.999999999Z
  // OffsetDateTime.MAX.toInstant = +1000000000-01-01T17:59:59.999999999Z
  private final val MinSeconds = OffsetDateTime.MIN.toInstant.getEpochSecond
  private final val MaxSeconds = OffsetDateTime.MAX.toInstant.getEpochSecond

  /** Max nanos of a second: 999.999.999 */
  private final val MaxNanos: Long = 999999999L

  private final def genForZoneOffset(seconds: Long): Gen[ZoneOffset] = {
    val diffToMin = seconds - MinSeconds
    val diffToMax = MaxSeconds - seconds
    val baseGen = if (diffToMin < 64800) {
      // for x == MinSeconds, the only allowed offset is 64800
      // for x == MinSeconds + 1, allowed offsets are [64799, 64800]
      // for x == MinSeconds + n, allowed offsets are [64800 - n, 64800]
      Gen.chooseNum((64800 - diffToMin).toInt, 64800)
    } else if (diffToMax < 64800) {
      // for x == MinSeconds, the only allowed offset is -64800
      // for x == MinSeconds + 1, allowed offsets are [-64800, -64799]
      // for x == MinSeconds + n, allowed offsets are [-64800, -64800 + n]
      Gen.chooseNum(-64800, (-64800 + diffToMax).toInt)
    } else {
      // x is far enough from both extremes so that we full range can be generated
      Gen.chooseNum(-64800, 64800)
    }
    baseGen map ZoneOffset.ofTotalSeconds
  }

  private final val genBaseWithLesser: Gen[(OffsetDateTime, OffsetDateTime)] = {
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
      xZoneOffset <- genForZoneOffset(xSeconds)
      yZoneOffset <- genForZoneOffset(ySeconds)
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = xInstant.atOffset(xZoneOffset)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = yInstant.atOffset(yZoneOffset)
      (x, y)
    }
  }

  private final val genBaseWithLesserOrEqual: Gen[(OffsetDateTime, OffsetDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y <= x', i,e. Gen(y).max == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0L, MaxNanos)
      yNanos <- Gen.chooseNum(0L, if (xSeconds == ySeconds) xNanos else MaxNanos)
      xZoneOffset <- genForZoneOffset(xSeconds)
      yZoneOffset <- genForZoneOffset(ySeconds)
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = xInstant.atOffset(xZoneOffset)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = yInstant.atOffset(yZoneOffset)
      (x, y)
    }
  }

  private final val genBaseWithGreater: Gen[(OffsetDateTime, OffsetDateTime)] = {
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
      xZoneOffset <- genForZoneOffset(xSeconds)
      yZoneOffset <- genForZoneOffset(ySeconds)
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = xInstant.atOffset(xZoneOffset)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = yInstant.atOffset(yZoneOffset)
      (x, y)
    }
  }

  private final val genBaseWithGreaterOrEqual: Gen[(OffsetDateTime, OffsetDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y >= x', i,e. Gen(y).min == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0L, MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos else 0, MaxNanos)
      xZoneOffset <- genForZoneOffset(xSeconds)
      yZoneOffset <- genForZoneOffset(ySeconds)
    } yield {
      val xInstant = Instant.ofEpochSecond(xSeconds, xNanos)
      val x = xInstant.atOffset(xZoneOffset)
      val yInstant = Instant.ofEpochSecond(ySeconds, yNanos)
      val y = yInstant.atOffset(yZoneOffset)
      (x, y)
    }
  }

}
