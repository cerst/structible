package io.cerst.structible.jsoniterscala

import java.util.UUID

import com.github.plokhotnyuk.jsoniter_scala.core.{
  JsonCodec,
  JsonReader,
  JsonWriter
}
import io.cerst.structible.Structible

object StructibleJsonCodec {

  // ==========================================================================
  // BOOLEAN
  // ==========================================================================
  def boolean[R](implicit structible: Structible[Boolean, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

  // ==========================================================================
  // DOUBLE
  // ==========================================================================
  def double[R](implicit structible: Structible[Double, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

  // ==========================================================================
  // FLOAT
  // ==========================================================================
  def float[R](implicit structible: Structible[Float, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

  // ==========================================================================
  // INT
  // ==========================================================================
  def int[R](implicit structible: Structible[Int, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

  // ==========================================================================
  // LONG
  // ==========================================================================
  def long[R](implicit structible: Structible[Long, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

  // ==========================================================================
  // SHORT
  // ==========================================================================
  def short[R](implicit structible: Structible[Short, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

  // ==========================================================================
  // STRING
  // ==========================================================================
  def string[R](implicit structible: Structible[String, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

  // ==========================================================================
  // UUID
  // ==========================================================================
  def uuid[R](implicit structible: Structible[UUID, R]): JsonCodec[R] = {
    new JsonCodec[R] {

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

}
