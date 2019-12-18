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

import java.time.Duration

import com.github.cerst.structible.core.constraint.instances.JavaDurationConstraints._
import com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax
import com.github.cerst.structible.core.testutil.NoShrink
import org.scalacheck.Gen
import org.scalatest.Assertions
import org.scalatest.freespec.AnyFreeSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

final class JavaDurationConstraintsSpec
    extends AnyFreeSpec
    with Assertions
    with ScalaCheckDrivenPropertyChecks
    with NoShrink {

  import JavaDurationConstraintsSpec._

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

object JavaDurationConstraintsSpec {

  /** see [[java.time.Duration#seconds]] */
  private final val MinSeconds = Long.MinValue

  /** see [[java.time.Duration#seconds]] */
  private final val MaxSeconds = Long.MaxValue

  /** see [[java.time.Duration#nanos]] */
  private final val MinNanos = 0L

  /** see [[java.time.Duration#nanos]] */
  private final val MaxNanos = 999999999L

  private final val genBaseWithShorter: Gen[(Duration, Duration)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   make sure that there is at least one 'y | y < x', i,e. Gen(x).min == 1 AND
      //   generate y accordingly, i.e. Gen(y).max == xNanos -1
      // else
      //   both nanos do not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(if (xSeconds == ySeconds) MinNanos + 1 else MinNanos, MaxNanos)
      yNanos <- Gen.chooseNum(MinNanos, if (xSeconds == ySeconds) xNanos - 1 else xNanos)
    } yield {
      val x = Duration.ofSeconds(xSeconds, xNanos)
      val y = Duration.ofSeconds(ySeconds, yNanos)
      (x, y)
    }
  }

  private final val genBaseWithShorterOrEqual: Gen[(Duration, Duration)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y <= x', i,e. Gen(y).max == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(MinNanos, MaxNanos)
      yNanos <- Gen.chooseNum(MinNanos, if (xSeconds == ySeconds) xNanos else MaxNanos)
    } yield {
      val x = Duration.ofSeconds(xSeconds, xNanos)
      val y = Duration.ofSeconds(ySeconds, yNanos)
      (x, y)
    }
  }

  private final val genBaseWithLonger: Gen[(Duration, Duration)] = {
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
    } yield {
      val x = Duration.ofSeconds(xSeconds, xNanos)
      val y = Duration.ofSeconds(ySeconds, yNanos)
      (x, y)
    }
  }

  private final val genBaseWithLongerOrEqual: Gen[(Duration, Duration)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y >= x', i,e. Gen(y).min == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0L, MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos else 0L, MaxNanos)
    } yield {
      val x = Duration.ofSeconds(xSeconds, xNanos)
      val y = Duration.ofSeconds(ySeconds, yNanos)
      (x, y)
    }
  }

}
