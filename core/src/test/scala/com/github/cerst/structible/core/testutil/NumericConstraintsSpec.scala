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

package com.github.cerst.structible.core.testutil

import com.github.cerst.structible.core.constraint.syntax.{ConstraintSyntax, GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}
import org.scalacheck.Gen
import org.scalacheck.Gen.Choose
import org.scalatest.{FreeSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

abstract class NumericConstraintsSpec[A: Numeric: Choose](dec: A => A, inc: A => A, globalMax: A, globalMin: A)(
  implicit greaterThanForA: GreaterThan[A],
  greaterThanOrEqualForA: GreaterThanOrEqual[A],
  lessThanForA: LessThan[A],
  lessThanOrEqualForA: LessThanOrEqual[A]
) extends FreeSpec
    with Matchers
    with ScalaCheckDrivenPropertyChecks
    with NoShrink {

  private val genBaseWithGreater: Gen[(A, A)] = {
    for {
      // decrement globalMax to ensure there is at least one greater value
      base <- Gen.chooseNum(globalMin, dec(globalMax))
      // increment base to ensure x > base
      larger <- Gen.chooseNum(inc(base), globalMax)
    } yield {
      (base, larger)
    }
  }

  private val genBaseWithGreaterOrEqual: Gen[(A, A)] = {
    for {
      base <- Gen.chooseNum(globalMin, globalMax)
      lessThanOrEqual <- Gen.chooseNum(base, globalMax)
    } yield {
      (base, lessThanOrEqual)
    }
  }

  private val genBaseWithLesser: Gen[(A, A)] = {
    for {
      // increment globalMin to ensure that there is at least one lesser value
      base <- Gen.chooseNum(inc(globalMin), globalMax)
      // decrement base to ensure x < base
      smaller <- Gen.chooseNum(globalMin, dec(base))
    } yield {
      (base, smaller)
    }
  }

  private val genBaseWithLesserOrEqual: Gen[(A, A)] = {
    for {
      base <- Gen.chooseNum(globalMin, globalMax)
      lessThanOrEqual <- Gen.chooseNum(globalMin, base)
    } yield {
      (base, lessThanOrEqual)
    }
  }

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
