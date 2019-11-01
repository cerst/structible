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

import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax
import com.github.cerst.structible.core.testutil.NoShrink
import org.scalacheck.Gen
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FreeSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

final class StringConstraintsSpec
    extends FreeSpec
    with Matchers
    with ScalaCheckDrivenPropertyChecks
    with TableDrivenPropertyChecks
    with NoShrink {

  private val genLengthWithLonger: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(0, StringConstraintsSpec.MaxStringSize - 1)
      yLength <- Gen.chooseNum(x + 1, StringConstraintsSpec.MaxStringSize)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  private val genLengthWithLongerOrEqual: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(0, StringConstraintsSpec.MaxStringSize)
      yLength <- Gen.chooseNum(x, StringConstraintsSpec.MaxStringSize)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  private val genLengthWithShorter: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(1, StringConstraintsSpec.MaxStringSize)
      yLength <- Gen.chooseNum(0, x - 1)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  private val genLengthWithShorterOrEqual: Gen[(Int, String)] = {
    for {
      x <- Gen.chooseNum(0, StringConstraintsSpec.MaxStringSize)
      yLength <- Gen.chooseNum(0, x)
      y <- Gen.listOfN(yLength, Gen.alphaNumChar).map(_.mkString)
    } yield {
      (x, y)
    }
  }

  // ===================================================================================================================
  // _.length <
  // ===================================================================================================================
  "'_.length < x' does not succeed with 'y.length >= x'" in {
    forAll(genLengthWithLongerOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<(x).apply(y)
        val expected = List(s"_.length < $x")

        assert(actual == expected)
    }
  }

  "'_.length < x' succeeds with 'y.length < x'" in {
    forAll(genLengthWithShorter) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // _.length <=
  // ===================================================================================================================
  "'_.length <= x' does not succeed with 'y.length > x'" in {
    forAll(genLengthWithLonger) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<=(x).apply(y)
        val expected = List(s"_.length <= $x")

        assert(actual == expected)
    }
  }

  "'_.length <= x' succeeds with 'y.length <= x'" in {
    forAll(genLengthWithShorterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.<=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // _.length >
  // ===================================================================================================================
  "'_.length > x' does not succeed with 'y.length <= x'" in {
    forAll(genLengthWithShorterOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>(x).apply(y)
        val expected = List(s"_.length > $x")

        assert(actual == expected)
    }
  }

  "'_.length > x' succeeds with 'y.length > x'" in {
    forAll(genLengthWithLonger) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // _.length >=
  // ===================================================================================================================
  "'_.length >= x' does not succeed with 'y.length < x'" in {
    forAll(genLengthWithShorter) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>=(x).apply(y)
        val expected = List(s"_.length >= $x")

        assert(actual == expected)
    }
  }

  "'_.length >= x' succeeds with 'y.length >= x'" in {
    forAll(genLengthWithLongerOrEqual) {
      case (x, y) =>
        val actual = ConstraintSyntax.length.>=(x).apply(y)
        val expected = List.empty[String]

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // matches
  // ===================================================================================================================
  val regexMatchFalseTrue =
    Table(("regex", "NOT matched", "matched"), ("""\d{1,3}\.\d{1,3}\.\d{1,3}""".r, "1234", "192.168.0.1"))

  "'_ matches x' does not succeed with y !matches x" in {

    forAll(regexMatchFalseTrue) {
      // 'testRegex' to avoid naming collision with Scalatest DSL term 'regex'
      case (testRegex, notMatched, _) =>
        val actual = ConstraintSyntax.matches(testRegex).apply(notMatched)
        val expected = List(s"_ matches $testRegex")

        assert(actual == expected)
    }
  }

  "'_ matches x' succeeds with y matches x" in {
    forAll(regexMatchFalseTrue) {
      // 'testRegex' to avoid naming collision with Scalatest DSL term 'regex'
      case (testRegex, _, matched) =>
        val actual = ConstraintSyntax.matches(testRegex).apply(matched)
        val expected = List(s"_ matches $testRegex")

        assert(actual == expected)
    }
  }

  // ===================================================================================================================
  // nonEmpty
  // ===================================================================================================================
  "'_.nonEmpty' does not succeed with y.isEmpty" in {
      val actual = ConstraintSyntax.nonEmpty[String].apply("")
      val expected = List("_.nonEmpty")

      assertResult(expected)(actual)
  }

  "'_.nonEmpty' succeeds with y.nonEmpty" in {
    forAll(Gen.nonEmptyListOf(Gen.alphaNumChar).map(_.mkString)) { nonEmptyString =>
      val actual = ConstraintSyntax.nonEmpty[String].apply(nonEmptyString)
      val expected = List.empty

      assertResult(expected)(actual)
    }
  }

}

object StringConstraintsSpec {

  val MaxStringSize = 1024

}
