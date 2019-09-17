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

package com.github.cerst.structible.avro4s

import com.github.cerst.structible.avro4s.ops.BicoderWithSchemaFor
import com.github.cerst.structible.core.Structible
import com.sksamuel.avro4s.{Decoder, Encoder, FieldMapper, SchemaFor}
import org.apache.avro.Schema

final class StructibleAvro4sOps[C, R](val structible: Structible[C, R]) extends AnyVal {

  def toBicoderWithSchemaFor(implicit cDecoder: Decoder[C],
                             cEncoder: Encoder[C],
                             cSchemaFor: SchemaFor[C]): BicoderWithSchemaFor[R] = {

    new BicoderWithSchemaFor[R] {
      override def encode(r: R, schema: Schema, fieldMapper: FieldMapper): AnyRef = {
        def c = structible.destruct(r)
        cEncoder.encode(c, schema, fieldMapper)
      }

      override def decode(value: Any, schema: Schema, fieldMapper: FieldMapper): R = {
        def c = cDecoder.decode(value, schema, fieldMapper)
        structible.construct(c)
      }

      override def schema(fieldMapper: FieldMapper): Schema = cSchemaFor.schema(fieldMapper)
    }
  }

}
