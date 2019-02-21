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

import java.util.UUID

import com.github.cerst.structible.core.Structible
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonCodec, JsonReader, JsonWriter}

// Jsoniter-Scala does not provide an implicit structure to derive json codecs - so, the derivations have to defined explicitly

// =====================================================================================================================
// BOOLEAN
// =====================================================================================================================
final class StructibleJsoniterScalaBooleanOps[R](val structible: Structible[Boolean, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      if (in isNextToken 'n') {
        in.readNullOrError(default, "expected Boolean value or null")
      } else {
        in.rollbackToken()
        val c = in.readBoolean()
        structible constructSafe c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsBoolean()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}

// =====================================================================================================================
// DOUBLE
// =====================================================================================================================
final class StructibleJsoniterScalaDoubleOps[R](val structible: Structible[Double, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      if (in isNextToken 'n') {
        in.readNullOrError(default, "expected Double value or null")
      } else {
        in.rollbackToken()
        val c = in.readDouble()
        structible constructSafe c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsDouble()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}

// =====================================================================================================================
// FLOAT
// =====================================================================================================================
final class StructibleJsoniterScalaFloatOps[R](val structible: Structible[Float, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      if (in isNextToken 'n') {
        in.readNullOrError(default, "expected Float value or null")
      } else {
        in.rollbackToken()
        val c = in.readFloat()
        structible constructSafe c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsFloat()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}

// =====================================================================================================================
// INT
// =====================================================================================================================
final class StructibleJsoniterScalaIntOps[R](val structible: Structible[Int, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      if (in isNextToken 'n') {
        in.readNullOrError(default, "expected Int value or null")
      } else {
        in.rollbackToken()
        val c = in.readInt()
        structible constructSafe c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsInt()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}

// =====================================================================================================================
// LONG
// =====================================================================================================================
final class StructibleJsoniterScalaLongOps[R](val structible: Structible[Long, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      if (in isNextToken 'n') {
        in.readNullOrError(default, "expected Long value or null")
      } else {
        in.rollbackToken()
        val c = in.readLong()
        structible constructSafe c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsLong()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}

// =====================================================================================================================
// SHORT
// =====================================================================================================================
final class StructibleJsoniterScalaShortOps[R](val structible: Structible[Short, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      if (in isNextToken 'n') {
        in.readNullOrError(default, "expected Short value or null")
      } else {
        in.rollbackToken()
        val c = in.readShort()
        structible constructSafe c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsShort()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}

// =====================================================================================================================
// STRING
// =====================================================================================================================
final class StructibleJsoniterScalaStringOps[R](val structible: Structible[String, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      val c = Option(in.readString(default = null))
      c map structible.constructSafe match {
        case None =>
          default
        case Some(Left(error)) =>
          in decodeError error
        case Some(Right(r)) =>
          r
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsString()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}

// =====================================================================================================================
// UUID
// =====================================================================================================================
final class StructibleJsoniterScalaUuidOps[R](val structible: Structible[UUID, R]) extends AnyVal {

  def toJsonCodec: JsonCodec[R] = new JsonCodec[R] {

    override def decodeValue(in: JsonReader, default: R): R = {
      val c = Option(in.readUUID(default = null))
      c map structible.constructSafe match {
        case None =>
          default
        case Some(Left(error)) =>
          in decodeError error
        case Some(Right(r)) =>
          r
      }
    }

    override def encodeValue(r: R, out: JsonWriter): Unit = {
      if (r != null) {
        out writeVal structible.destruct(r)
      } else {
        out.writeNull()
      }
    }

    override def nullValue: R = null.asInstanceOf[R]

    override def decodeKey(in: JsonReader): R = {
      val c = in.readKeyAsUUID()
      structible constructSafe c match {
        case Left(error) =>
          in decodeError error
        case Right(r) =>
          r
      }
    }

    override def encodeKey(r: R, out: JsonWriter): Unit = {
      out writeKey structible.destruct(r)
    }
  }

}