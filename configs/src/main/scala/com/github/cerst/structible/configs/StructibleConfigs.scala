package com.github.cerst.structible.configs

import com.github.cerst.structible.Constructible
import configs.Configs
object StructibleConfigs {

  def apply[C: Configs, R](
    implicit constructible: Constructible[C, R]
  ): Configs[R] = {
    Configs from { (config, path) =>
      Configs[C].get(config, path) map constructible.constructUnsafe
    }
  }
}
