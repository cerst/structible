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
import com.github.plokhotnyuk.jsoniter_scala.core._

object ops {

  // =====================================================================================================================
  // BigDecimal
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaBigIntOps[R](val structible: Structible[BigInt, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[BigInt, R](
      "BigInt",
      structible,
      rNullValue,
      readC = _.readBigInt(default = null),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsBigInt(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // BigInt
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaBigDecimalOps[R](val structible: Structible[BigDecimal, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[BigDecimal, R](
      "BigDecimal",
      structible,
      rNullValue,
      readC = _.readBigDecimal(default = null),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsBigDecimal(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // BOOLEAN
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaBooleanOps[R](val structible: Structible[Boolean, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] =
      newJsonCodec[Boolean, R](
        "Boolean",
        structible,
        rNullValue,
        readC = _.readBoolean,
        writeVal = _.writeVal,
        readKeyAsC = _.readKeyAsBoolean(),
        writeKey = _.writeKey
      )
  }

  // =====================================================================================================================
  // DOUBLE
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaDoubleOps[R](val structible: Structible[Double, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[Double, R](
      "Double",
      structible,
      rNullValue,
      readC = _.readDouble(),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsDouble(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // DURATION
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaDurationOps[R](val structible: Structible[Duration, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[Duration, R](
      "Duration",
      structible,
      rNullValue,
      readC = _.readDuration(default = null),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsDuration(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // FLOAT
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaFloatOps[R](val structible: Structible[Float, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[Float, R](
      "Float",
      structible,
      rNullValue,
      readC = _.readFloat(),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsFloat(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // INT
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaIntOps[R](val structible: Structible[Int, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[Int, R](
      "Int",
      structible,
      rNullValue,
      readC = _.readInt(),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsInt(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // LONG
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaLongOps[R](val structible: Structible[Long, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[Long, R](
      "Long",
      structible,
      rNullValue,
      readC = _.readLong(),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsLong(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // OFFSET DATE TIME
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaOffsetDateTimeOps[R](val structible: Structible[OffsetDateTime, R])
      extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[OffsetDateTime, R](
      "OffsetDateTime",
      structible,
      rNullValue,
      readC = _.readOffsetDateTime(default = null),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsOffsetDateTime(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // SHORT
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaShortOps[R](val structible: Structible[Short, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[Short, R](
      "Short",
      structible,
      rNullValue,
      readC = _.readShort(),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsShort(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // STRING
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaStringOps[R](val structible: Structible[String, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[String, R](
      "String",
      structible,
      rNullValue,
      readC = _.readString(default = null),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsString(),
      writeKey = _.writeKey
    )
  }

  // =====================================================================================================================
  // UUID
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaUuidOps[R](val structible: Structible[UUID, R]) extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[UUID, R](
      "UUID",
      structible,
      rNullValue,
      readC = _.readUUID(default = null),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsUUID(),
      writeKey = _.writeKey
    )

  }

  // =====================================================================================================================
  // ZONED DATE TIME
  // =====================================================================================================================
  implicit class StructibleJsoniterScalaZonedDateTimeOps[R](val structible: Structible[ZonedDateTime, R])
      extends AnyVal {

    /**
      * @param rNullValue <br/>Must be set <b>explicitly</b> to <i>null.asInstanceOf[R]</i> with <i>R</i> being the actual
      *                   type (e.g. <i>UserId</i>) if and only if <i>R</i> is a subtype of <i>AnyVal</i>.
      *                   <br/>
      *                   This required to work around [[https://github.com/scala/bug/issues/8097 this compiler bug]].
      *                   <br/>
      *                   Otherwise, the created codec throws a <i>NullPointerException</i> at runtime.
      */
    def toJsonCodec(rNullValue: R = null.asInstanceOf[R]): JsonCodec[R] = newJsonCodec[ZonedDateTime, R](
      "ZonedDateTime",
      structible,
      rNullValue,
      readC = _.readZonedDateTime(default = null),
      writeVal = _.writeVal,
      readKeyAsC = _.readKeyAsZonedDateTime(),
      writeKey = _.writeKey
    )
  }

}
