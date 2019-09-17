package com.github.cerst.structible.pureconfig

import com.github.cerst.structible.core.{Constructible, Destructible, Structible}

object ops {

  implicit final def toConstructiblePureconfigOps[C, R](
    constructible: Constructible[C, R]
  ): ConstructiblePureconfigOps[C, R] = new ConstructiblePureconfigOps(constructible)

  implicit final def toDestructiblePureconfigOps[C, R](
    destructible: Destructible[C, R]
  ): DestructiblePureconfigOps[C, R] = new DestructiblePureconfigOps(destructible)

  implicit final def toStructiblePureconfigOps[C,R](structible: Structible[C,R]): StructiblePureconfigOps[C,R] = {
    new StructiblePureconfigOps(structible)
  }

}
