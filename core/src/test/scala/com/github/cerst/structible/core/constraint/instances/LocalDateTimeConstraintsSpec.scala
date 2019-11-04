package com.github.cerst.structible.core.constraint.instances

import java.time.{LocalDateTime, ZoneOffset}

import com.github.cerst.structible.core.constraint.instances.LocalDateTimeConstraints._
import com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax
import com.github.cerst.structible.core.testutil.NoShrink
import org.scalacheck.Gen
import org.scalatest.{FreeSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

final class LocalDateTimeConstraintsSpec
    extends FreeSpec
    with Matchers
    with ScalaCheckDrivenPropertyChecks
    with NoShrink {

  import LocalDateTimeConstraintsSpec._

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

private object LocalDateTimeConstraintsSpec {

  // given that the LocalDateTime constructor accepts an offset, re-use thee definitions from the respective test
  // slight difference: for some reason, nanos must be an Int rather than a Long is this case
  private final val MinSeconds: Long = LocalDateTime.MIN.toEpochSecond(ZoneOffset.UTC)
  private final val MaxSeconds: Long = LocalDateTime.MAX.toEpochSecond(ZoneOffset.UTC)

  private final val MinNanos = 0

  /** Max nanos of a second: 999.999.999 */
  private final val MaxNanos: Int = 999999999

  final val genBaseWithLesser: Gen[(LocalDateTime, LocalDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   make sure that there is at least one 'y | y < x', i,e. Gen(x).min == 1 AND
      //   generate y accordingly, i.e. Gen(y).max == xNanos -1
      // else
      //   both nanos do not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(if (xSeconds == ySeconds) 1 else 0, MaxNanos)
      yNanos <- Gen.chooseNum(0, if (xSeconds == ySeconds) xNanos - 1 else MaxNanos)
    } yield {
      val x = LocalDateTime.ofEpochSecond(xSeconds, xNanos, ZoneOffset.UTC)
      val y = LocalDateTime.ofEpochSecond(ySeconds, yNanos, ZoneOffset.UTC)
      (x, y)
    }
  }

  final val genBaseWithLesserOrEqual: Gen[(LocalDateTime, LocalDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(MinSeconds, xSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y <= x', i,e. Gen(y).max == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0, MaxNanos)
      yNanos <- Gen.chooseNum(0, if (xSeconds == ySeconds) xNanos else MaxNanos)
    } yield {
      val x = LocalDateTime.ofEpochSecond(xSeconds, xNanos, ZoneOffset.UTC)
      val y = LocalDateTime.ofEpochSecond(ySeconds, yNanos, ZoneOffset.UTC)
      (x, y)
    }
  }

  final val genBaseWithGreater: Gen[(LocalDateTime, LocalDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   make sure that there is at least one 'y | y > x', i,e. Gen(x).max == MaxNanos -1 AND
      //   generate y accordingly, i.e. Gen(y).min == xNanos -1
      // else
      //   both nanos do not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0, if (xSeconds == ySeconds) MaxNanos - 1 else MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos + 1 else 0, MaxNanos)
    } yield {
      val x = LocalDateTime.ofEpochSecond(xSeconds, xNanos, ZoneOffset.UTC)
      val y = LocalDateTime.ofEpochSecond(ySeconds, yNanos, ZoneOffset.UTC)
      (x, y)
    }
  }

  final val genBaseWithGreaterOrEqual: Gen[(LocalDateTime, LocalDateTime)] = {
    for {
      xSeconds <- Gen.chooseNum(MinSeconds, MaxSeconds)
      ySeconds <- Gen.chooseNum(xSeconds, MaxSeconds)
      // if seconds are equal, nanos must make the difference and thus
      //   generate 'y | y >= x', i,e. Gen(y).min == xNanos
      // else
      //   nano does not matter and can be generated using the full range
      xNanos <- Gen.chooseNum(0, MaxNanos)
      yNanos <- Gen.chooseNum(if (xSeconds == ySeconds) xNanos else 0, MaxNanos)
    } yield {
      val x = LocalDateTime.ofEpochSecond(xSeconds, xNanos, ZoneOffset.UTC)
      val y = LocalDateTime.ofEpochSecond(ySeconds, yNanos, ZoneOffset.UTC)
      (x, y)
    }
  }

}
