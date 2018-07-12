package io.cerst.structible.quill

import io.cerst.structible.Structible
import io.getquill.MappedEncoding

trait StructibleMappedEncoding {

  implicit def encodeStructible[P, W](
    implicit structible: Structible[P, W]
  ): MappedEncoding[W, P] = {
    MappedEncoding(structible.destruct)
  }

  implicit def decodeStructible[P, W](
    implicit structible: Structible[P, W]
  ): MappedEncoding[P, W] = {
    MappedEncoding(structible.constructUnsafe)
  }

}

object StructibleMappedEncoding extends StructibleMappedEncoding
