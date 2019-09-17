package com.github.cerst.structible.pureconfig

import com.github.cerst.structible.core.Constructible
import pureconfig.ConfigReader

final class ConstructiblePureconfigOps[C, R](val constructible: Constructible[C, R]) extends AnyVal {

  def toConfigReader(implicit configReader: ConfigReader[C]): ConfigReader[R] = {
    configReader.map(constructible.construct)
  }

}
