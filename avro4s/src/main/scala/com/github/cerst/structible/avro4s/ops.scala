package com.github.cerst.structible.avro4s

import com.github.cerst.structible.core.{Constructible, Destructible, Structible}
import com.sksamuel.avro4s.{Decoder, Encoder, SchemaFor}

object ops {

  trait BicoderWithSchemaFor[R] extends Decoder[R] with Encoder[R] with SchemaFor[R]

  implicit def toConstructibleAvro4sOps[C,R](constructible: Constructible[C,R]): ConstructibleAvro4sOps[C,R] = {
    new ConstructibleAvro4sOps[C,R](constructible)
  }

  implicit def toDestructibleAvro4sOps[C,R](destructible: Destructible[C,R]): DestructibleAvro4sOps[C,R] = {
    new DestructibleAvro4sOps[C,R](destructible)
  }

  implicit def toStructibleAvro4sOps[C,R](structible: Structible[C,R]): StructibleAvro4sOps[C,R] = {
    new StructibleAvro4sOps[C,R](structible)
  }

}
