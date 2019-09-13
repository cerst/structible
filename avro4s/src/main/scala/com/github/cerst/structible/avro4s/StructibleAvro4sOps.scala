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
