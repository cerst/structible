package com.github.cerst.structible.core

import scala.reflect.ClassTag

final case class RName(value: String)

object RName {

  def apply[A: ClassTag]: RName = RName(implicitly[ClassTag[A]].runtimeClass.getSimpleName)
}
