package com.github.cerst.structible

import com.github.cerst.structible.core.constraint.syntax.ConstraintSyntax
import com.github.ghik.silencer.silent

import scala.reflect.ClassTag

package object core {

  /**
    * ConstraintSyntax alias to allow for writing constraint expressions such as <i>c >= 0 && c <= 100</i> when
    * creating a [[com.github.cerst.structible.core.Structible Structible]] instance.
    */
  val c: ConstraintSyntax.type = ConstraintSyntax

  /** Name resolution via companion object */
  @silent // silence the parameter not being used (only required for its type to avoid library users needing to explicitly specify type parameters)
  implicit def companionToRName[A](a: A)(implicit classTag: ClassTag[A]): RName = {
    // Nested classes don't have a simple name
    val value = classTag.runtimeClass.getName.reverse.drop(1).takeWhile(_ != '.').reverse
    RName(value)
  }

  /** Manually provided name */
  implicit def stringToRName(string: String): RName = RName(string)

}