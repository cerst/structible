/*
 * Copyright (c) 2018 Constantin Gerstberger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

  /** Name resolution via (companion) object */
  @silent // silence the parameter not being used (only required for its type to avoid library users needing to explicitly specify type parameters)
  implicit def objectToRName[A](a: A)(implicit classTag: ClassTag[A]): RName = {
    // Nested classes don't have a simple name
    RName(isObject = true)
  }

  /** Manually provided name */
  implicit def stringToRName(string: String): RName = RName(string)

}
