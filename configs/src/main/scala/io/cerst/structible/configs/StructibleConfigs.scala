package io.cerst.structible.configs

import configs.Configs
import io.cerst.structible.Constructible

object StructibleConfigs {

  def apply[C: Configs, R](
    implicit constructible: Constructible[C, R]
  ): Configs[R] = {
    Configs from { (config, path) =>
      Configs[C].get(config, path) map constructible.constructUnsafe
    }
  }
}
