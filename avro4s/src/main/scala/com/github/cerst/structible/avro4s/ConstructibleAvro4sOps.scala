package com.github.cerst.structible.avro4s

import com.github.cerst.structible.core._
import com.sksamuel.avro4s.Decoder

final class ConstructibleAvro4sOps[C,R](val constructible: Constructible[C,R]) extends AnyVal {

   def toDecoder(implicit cDecoder: Decoder[C]): Decoder[R] = cDecoder.map(constructible.construct)

}
