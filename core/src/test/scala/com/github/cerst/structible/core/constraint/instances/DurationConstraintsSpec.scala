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

import com.github.cerst.structible.core.constraint.instances.DurationConstraints._
import com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax
import com.github.cerst.structible.core.testutil.NoShrink
import org.scalacheck.Gen
import org.scalatest.Assertions
import org.scalatest.freespec.AnyFreeSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.concurrent.duration.{Duration, NANOSECONDS}

final class DurationConstraintsSpec extends AnyFreeSpec with Assertions with ScalaCheckDrivenPropertyChecks with NoShrink {

  import DurationConstraintsSpec._

  // ===================================================================================================================
  // <
  // ===================================================================================================================
  "'_ < x' does not succeed with y >= x" in {
    forAll(genBaseWithLongerOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.<(x).apply(y)
        val expected = List(s"_ < $x")

        assert(actual == expected)
    }
  }

  "'_ < x' succeeds with y < x" in {
    forAll(genBaseWithShorter) {
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
    forAll(genBaseWithLonger) {
      case (x, y) =>
        val actual = ConstraintSyntax.<=(x).apply(y)
        val expected = List(s"_ <= $x")

        assert(actual == expected)
    }
  }

  "'_ <= x' succeeds with y <= x" in {
    forAll(genBaseWithShorterOrEqual) {
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
    forAll(genBaseWithShorterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.>(x).apply(y)
        val expected = List(s"_ > $x")

        assert(actual == expected)
    }
  }

  "'_ > x' succeeds with y > x" in {
    forAll(genBaseWithLonger) {
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
    forAll(genBaseWithShorter) {
      case (x, y) =>
        val actual = ConstraintSyntax.>=(x).apply(y)
        val expected = List(s"_ >= $x")

        assert(actual == expected)
    }
  }

  "'_ >= x' succeeds with y >= x" in {
    forAll(genBaseWithLongerOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.>=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

}

object DurationConstraintsSpec {

  /** see [[scala.concurrent.duration.FiniteDuration#bounded]] */
  // Long.MinValue != - Long.MaxValue == Long.MinValue + 1
  private final val MinNanos: Long = Long.MinValue + 1

  /** see [[scala.concurrent.duration.FiniteDuration#bounded]] */
  private final val MaxNanos: Long = Long.MaxValue

  private final val genBaseWithShorter: Gen[(Duration, Duration)] = {
    for {
      xNanos <- Gen.chooseNum(MinNanos + 1, MaxNanos)
      yNanos <- Gen.chooseNum(MinNanos, xNanos - 1)
    } yield {
      val x = Duration(xNanos, NANOSECONDS)
      val y = Duration(yNanos, NANOSECONDS)
      (x, y)
    }
  }

  private final val genBaseWithShorterOrEqual: Gen[(Duration, Duration)] = {
    for {
      xNanos <- Gen.chooseNum(MinNanos, MaxNanos)
      yNanos <- Gen.chooseNum(MinNanos, xNanos)
    } yield {
      val x = Duration(xNanos, NANOSECONDS)
      val y = Duration(yNanos, NANOSECONDS)
      (x, y)
    }
  }

  private final val genBaseWithLonger: Gen[(Duration, Duration)] = {
    for {
      xNanos <- Gen.chooseNum(MinNanos, MaxNanos - 1)
      yNanos <- Gen.chooseNum(xNanos + 1, MaxNanos)
    } yield {
      val x = Duration(xNanos, NANOSECONDS)
      val y = Duration(yNanos, NANOSECONDS)
      (x, y)
    }
  }

  private final val genBaseWithLongerOrEqual: Gen[(Duration, Duration)] = {
    for {
      xNanos <- Gen.chooseNum(MinNanos, MaxNanos)
      yNanos <- Gen.chooseNum(xNanos, MaxNanos)
    } yield {
      val x = Duration(xNanos, NANOSECONDS)
      val y = Duration(yNanos, NANOSECONDS)
      (x, y)
    }
  }

}
