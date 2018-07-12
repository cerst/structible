package io.cerst.structible.jsoniterscala

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonCodec, JsonReader, JsonWriter}
import io.cerst.structible.Structible

object StructibleCodec {

  // ==========================================================================
  // BOOLEAN
  // ==========================================================================
  def boolean[W](implicit structible: Structible[Boolean, W]): JsonCodec[W] = {
    new JsonCodec[W] {

      override def decodeValue(in: JsonReader, default: W): W = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, "expected Boolean value or null")
        } else {
          in.rollbackToken()
          val p = in.readBoolean()
          structible constructSafe p match {
            case Left(error) =>
              in decodeError error
            case Right(w) =>
              w
          }
        }
      }

      override def encodeValue(w: W, out: JsonWriter): Unit = {
        if (w != null) {
          out writeVal structible.destruct(w)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: W = null.asInstanceOf[W]

      override def decodeKey(in: JsonReader): W = {
        val p = in.readKeyAsBoolean()
        structible constructSafe p match {
          case Left(error) =>
            in decodeError error
          case Right(w) =>
            w
        }
      }

      override def encodeKey(w: W, out: JsonWriter): Unit = {
        out writeKey structible.destruct(w)
      }
    }
  }

  // ==========================================================================
  // DOUBLE
  // ==========================================================================
  def double[W](implicit structible: Structible[Double, W]): JsonCodec[W] = {
    new JsonCodec[W] {

      override def decodeValue(in: JsonReader, default: W): W = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, "expected Double value or null")
        } else {
          in.rollbackToken()
          val p = in.readDouble()
          structible constructSafe p match {
            case Left(error) =>
              in decodeError error
            case Right(w) =>
              w
          }
        }
      }

      override def encodeValue(w: W, out: JsonWriter): Unit = {
        if (w != null) {
          out writeVal structible.destruct(w)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: W = null.asInstanceOf[W]

      override def decodeKey(in: JsonReader): W = {
        val p = in.readKeyAsDouble()
        structible constructSafe p match {
          case Left(error) =>
            in decodeError error
          case Right(w) =>
            w
        }
      }

      override def encodeKey(w: W, out: JsonWriter): Unit = {
        out writeKey structible.destruct(w)
      }
    }
  }

  // ==========================================================================
  // FLOAT
  // ==========================================================================
  def float[W](implicit structible: Structible[Float, W]): JsonCodec[W] = {
    new JsonCodec[W] {

      override def decodeValue(in: JsonReader, default: W): W = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, "expected Float value or null")
        } else {
          in.rollbackToken()
          val p = in.readFloat()
          structible constructSafe p match {
            case Left(error) =>
              in decodeError error
            case Right(w) =>
              w
          }
        }
      }

      override def encodeValue(w: W, out: JsonWriter): Unit = {
        if (w != null) {
          out writeVal structible.destruct(w)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: W = null.asInstanceOf[W]

      override def decodeKey(in: JsonReader): W = {
        val p = in.readKeyAsFloat()
        structible constructSafe p match {
          case Left(error) =>
            in decodeError error
          case Right(w) =>
            w
        }
      }

      override def encodeKey(w: W, out: JsonWriter): Unit = {
        out writeKey structible.destruct(w)
      }
    }
  }

  // ==========================================================================
  // INT
  // ==========================================================================
  def int[W](implicit structible: Structible[Int, W]): JsonCodec[W] = {
    new JsonCodec[W] {

      override def decodeValue(in: JsonReader, default: W): W = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, "expected Int value or null")
        } else {
          in.rollbackToken()
          val p = in.readInt()
          structible constructSafe p match {
            case Left(error) =>
              in decodeError error
            case Right(w) =>
              w
          }
        }
      }

      override def encodeValue(w: W, out: JsonWriter): Unit = {
        if (w != null) {
          out writeVal structible.destruct(w)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: W = null.asInstanceOf[W]

      override def decodeKey(in: JsonReader): W = {
        val p = in.readKeyAsInt()
        structible constructSafe p match {
          case Left(error) =>
            in decodeError error
          case Right(w) =>
            w
        }
      }

      override def encodeKey(w: W, out: JsonWriter): Unit = {
        out writeKey structible.destruct(w)
      }
    }
  }

  // ==========================================================================
  // LONG
  // ==========================================================================
  def long[W](implicit structible: Structible[Long, W]): JsonCodec[W] = {
    new JsonCodec[W] {

      override def decodeValue(in: JsonReader, default: W): W = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, "expected Long value or null")
        } else {
          in.rollbackToken()
          val p = in.readLong()
          structible constructSafe p match {
            case Left(error) =>
              in decodeError error
            case Right(w) =>
              w
          }
        }
      }

      override def encodeValue(w: W, out: JsonWriter): Unit = {
        if (w != null) {
          out writeVal structible.destruct(w)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: W = null.asInstanceOf[W]

      override def decodeKey(in: JsonReader): W = {
        val p = in.readKeyAsLong()
        structible constructSafe p match {
          case Left(error) =>
            in decodeError error
          case Right(w) =>
            w
        }
      }

      override def encodeKey(w: W, out: JsonWriter): Unit = {
        out writeKey structible.destruct(w)
      }
    }
  }

  // ==========================================================================
  // SHORT
  // ==========================================================================
  def short[W](implicit structible: Structible[Short, W]): JsonCodec[W] = {
    new JsonCodec[W] {

      override def decodeValue(in: JsonReader, default: W): W = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, "expected Short value or null")
        } else {
          in.rollbackToken()
          val p = in.readShort()
          structible constructSafe p match {
            case Left(error) =>
              in decodeError error
            case Right(w) =>
              w
          }
        }
      }

      override def encodeValue(w: W, out: JsonWriter): Unit = {
        if (w != null) {
          out writeVal structible.destruct(w)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: W = null.asInstanceOf[W]

      override def decodeKey(in: JsonReader): W = {
        val p = in.readKeyAsShort()
        structible constructSafe p match {
          case Left(error) =>
            in decodeError error
          case Right(w) =>
            w
        }
      }

      override def encodeKey(w: W, out: JsonWriter): Unit = {
        out writeKey structible.destruct(w)
      }
    }
  }

  // ==========================================================================
  // STRING
  // ==========================================================================
  def string[W](implicit structible: Structible[String, W]): JsonCodec[W] = {
    new JsonCodec[W] {

      override def decodeValue(in: JsonReader, default: W): W = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, "expected String value or null")
        } else {
          in.rollbackToken()
          // TODO: null
          val p = in.readString(null)
          structible constructSafe p match {
            case Left(error) =>
              in decodeError error
            case Right(w) =>
              w
          }
        }
      }

      override def encodeValue(w: W, out: JsonWriter): Unit = {
        if (w != null) {
          out writeVal structible.destruct(w)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: W = null.asInstanceOf[W]

      override def decodeKey(in: JsonReader): W = {
        val p = in.readKeyAsString()
        structible constructSafe p match {
          case Left(error) =>
            in decodeError error
          case Right(w) =>
            w
        }
      }

      override def encodeKey(w: W, out: JsonWriter): Unit = {
        out writeKey structible.destruct(w)
      }
    }
  }

}
