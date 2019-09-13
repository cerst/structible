package com.github.cerst.structible.avro4s

import com.github.cerst.structible.core.Destructible
import com.sksamuel.avro4s.{Encoder, SchemaFor}

final class DestructibleAvro4sOps[C, R](val destructible: Destructible[C, R]) extends AnyVal {

  def toEncoder(implicit cEncoder: Encoder[C]): Encoder[R] = cEncoder.comap(destructible.destruct)

  // the SchemaFor for R is the same as for C because R is serialized just like C
  // (unless we want to add custom Avro type refinements which are e.g. not supported by Schema Registry)
  def toSchemaFor(implicit cSchemeFor: SchemaFor[C]): SchemaFor[R] = cSchemeFor.map(identity)

}
