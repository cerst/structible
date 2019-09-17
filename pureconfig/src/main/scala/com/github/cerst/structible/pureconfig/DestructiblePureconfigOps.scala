package com.github.cerst.structible.pureconfig

import com.github.cerst.structible.core.Destructible
import pureconfig.ConfigWriter

final class DestructiblePureconfigOps[C, R](val destructible: Destructible[C, R]) extends AnyVal {

  def toConfigWriter(implicit configWriter: ConfigWriter[C]): ConfigWriter[R] = {
    configWriter.contramap(destructible.destruct)
  }

}
