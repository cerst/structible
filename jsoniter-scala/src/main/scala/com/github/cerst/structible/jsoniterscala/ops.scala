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

package com.github.cerst.structible.jsoniterscala

import java.time.{Duration, OffsetDateTime, ZonedDateTime}
import java.util.UUID

import com.github.cerst.structible.core.Structible

object ops {

  implicit def toStructibleJsoniterScalaBigIntOps[R](
    structible: Structible[BigInt, R]
  ): StructibleJsoniterScalaBigIntOps[R] = {
    new StructibleJsoniterScalaBigIntOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaBigDecimalOps[R](
    structible: Structible[BigDecimal, R]
  ): StructibleJsoniterScalaBigDecimalOps[R] = {
    new StructibleJsoniterScalaBigDecimalOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaBooleanOps[R](
    structible: Structible[Boolean, R]
  ): StructibleJsoniterScalaBooleanOps[R] = {
    new StructibleJsoniterScalaBooleanOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaDoubleOps[R](
    structible: Structible[Double, R]
  ): StructibleJsoniterScalaDoubleOps[R] = {
    new StructibleJsoniterScalaDoubleOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaDurationOps[R](
    structible: Structible[Duration, R]
  ): StructibleJsoniterScalaDurationOps[R] = {
    new StructibleJsoniterScalaDurationOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaIntOps[R](structible: Structible[Int, R]): StructibleJsoniterScalaIntOps[R] = {
    new StructibleJsoniterScalaIntOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaFloatOps[R](
    structible: Structible[Float, R]
  ): StructibleJsoniterScalaFloatOps[R] = {
    new StructibleJsoniterScalaFloatOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaLongOps[R](
    structible: Structible[Long, R]
  ): StructibleJsoniterScalaLongOps[R] = {
    new StructibleJsoniterScalaLongOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaOffsetDateTimeOps[R](
    structible: Structible[OffsetDateTime, R]
  ): StructibleJsoniterScalaOffsetDateTimeOps[R] = {
    new StructibleJsoniterScalaOffsetDateTimeOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaShortOps[R](
    structible: Structible[Short, R]
  ): StructibleJsoniterScalaShortOps[R] = {
    new StructibleJsoniterScalaShortOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaStringOps[R](
    structible: Structible[String, R]
  ): StructibleJsoniterScalaStringOps[R] = {
    new StructibleJsoniterScalaStringOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaUuidOps[R](
    structible: Structible[UUID, R]
  ): StructibleJsoniterScalaUuidOps[R] = {
    new StructibleJsoniterScalaUuidOps[R](structible)
  }

  implicit def toStructibleJsoniterScalaZonedDateTimeOps[R](
    structible: Structible[ZonedDateTime, R]
  ): StructibleJsoniterScalaZonedDateTimeOps[R] = {
    new StructibleJsoniterScalaZonedDateTimeOps[R](structible)
  }

}
