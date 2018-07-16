package io.cerst.structible.quill

import io.cerst.structible.{Constructible, Destructible}
import io.getquill.MappedEncoding

trait StructibleMappedEncoding {

  implicit def encodeStructible[C, R](
    implicit destructible: Destructible[C, R]
  ): MappedEncoding[R, C] = {
    MappedEncoding(destructible.destruct)
  }

  implicit def decodeStructible[C, R](
    implicit constructible: Constructible[C, R]
  ): MappedEncoding[C, R] = {
    MappedEncoding(constructible.constructUnsafe)
  }

}

object StructibleMappedEncoding extends StructibleMappedEncoding