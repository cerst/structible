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
