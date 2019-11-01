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

import java.time.ZonedDateTime

import com.github.cerst.structible.core.constraint.syntax.{GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}

/**
  * Implementation Note:
  * <p>
  * All constraints implement an instant-based comparison based on [[java.time.ZonedDateTime#isAfter isAfter]],
  * [[java.time.ZonedDateTime#isBefore isBefore]] or [[java.time.ZonedDateTime#isEqual isEqual]]. This is because:
  * <ul>
  * <li>[[java.time.ZonedDateTime#compareTo compareTo]] has different semantics</i>
  * <li>there is no <i>isBeforeOrSame</i> or <i>isAfterOrSame</i> for [[java.time.ZonedDateTime ZonedDateTime]]</li>
  * <li>(re-) implementation is more efficient that calling e.g. [[java.time.ZonedDateTime#isBefore isBefore]] and [[java.time.ZonedDateTime#isEqual isEqual]] in sequence</li>
  * <li>using the same implementation for all constraints instead of e.g. using [[java.time.ZonedDateTime#isBefore isBefore]] for implementing [[com.github.cerst.structible.core.constraint.syntax.LessThan]] increases readability</li>
  * <ul>
  */
trait ZonedDateTimeConstraints {

  private final def nanos(zonedDateTime: ZonedDateTime): Int = zonedDateTime.toLocalTime.getNano

  implicit final val lessThanForZonedDateTime: LessThan[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def lt = (ySeconds < xSeconds) || (ySeconds == xSeconds && nanos(y) < nanos(x))
    if (lt) List.empty else List(s"_ < $x")
  }

  implicit final val lessThanOrEqualForZonedDateTime: LessThanOrEqual[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def lte = (ySeconds < xSeconds) || (ySeconds == xSeconds && nanos(y) <= nanos(x))
    if (lte) List.empty else List(s"_ <= $x")
  }

  implicit final val greaterThanForZonedDateTime: GreaterThan[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def gt = (ySeconds > xSeconds) || (ySeconds == xSeconds && nanos(y) > nanos(x))
    if (gt) List.empty else List(s"_ > $x")
  }

  implicit final val greaterThanOrEqualForZonedDateTime: GreaterThanOrEqual[ZonedDateTime] = x => { y =>
    val xSeconds = x.toEpochSecond
    val ySeconds = y.toEpochSecond
    def gte = (ySeconds > xSeconds) || (ySeconds == xSeconds && nanos(y) >= nanos(x))
    if (gte) List.empty else List(s"_ >= $x")
  }

}

object ZonedDateTimeConstraints extends ZonedDateTimeConstraints
