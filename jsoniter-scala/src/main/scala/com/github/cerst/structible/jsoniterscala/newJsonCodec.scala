package com.github.cerst.structible.jsoniterscala

import com.github.cerst.structible.core.Structible
import com.github.plokhotnyuk.jsoniter_scala.core._

// Jsoniter-Scala does not provide an implicit structure to derive json codecs - so, the derivations have to defined explicitly

private[jsoniterscala] object newJsonCodec {

  final def apply[C, R](cName: String,
                        structible: Structible[C, R],
                        rNullValue: R,
                        readC: JsonReader => C,
                        writeVal: JsonWriter => C => Unit,
                        readKeyAsC: JsonReader => C,
                        writeKey: JsonWriter => C => Unit): JsonCodec[R] = {

    new JsonCodec[R] {
      override def decodeValue(in: JsonReader, default: R): R = {
        if (in isNextToken 'n') {
          in.readNullOrError(default, s"expected $cName value or null")
        } else {
          in.rollbackToken()
          def c = readC(in)
          structible constructEither c match {
            case Left(error) =>
              in decodeError error
            case Right(r) =>
              r
          }
        }
      }

      override def encodeValue(r: R, out: JsonWriter): Unit = {
        if (r != null) {
          def c = structible.destruct(r)
          writeVal(out)(c)
        } else {
          out.writeNull()
        }
      }

      override def nullValue: R = rNullValue

      override def decodeKey(in: JsonReader): R = {
        def c = readKeyAsC(in)
        structible constructEither c match {
          case Left(error) =>
            in decodeError error
          case Right(r) =>
            r
        }
      }

      override def encodeKey(r: R, out: JsonWriter): Unit = {
        def c = structible.destruct(r)
        writeKey(out)(c)
      }
    }
  }

}
