package com.github.cerst.structible.pureconfig

import com.github.cerst.structible.core.Structible
import pureconfig.ConfigConvert

final class StructiblePureconfigOps[C,R](val structible: Structible[C,R]) extends AnyVal {

  def toConfigConvert(implicit configConvert: ConfigConvert[C]): ConfigConvert[R] = {
    configConvert.xmap(structible.construct, structible.destruct)
  }

}
