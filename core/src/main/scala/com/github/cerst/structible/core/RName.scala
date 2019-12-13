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

package com.github.cerst.structible.core

import scala.reflect.ClassTag

final case class RName(value: String)

object RName {

  def apply[A: ClassTag]: RName = apply(isObject = false)

  def apply[A](isObject: Boolean)(implicit classTag: ClassTag[A]): RName = {
    // the name of (companion) objects always end with '$'
    // getSimpleName causes 'java.lang.InternalError: Malformed class name' for nested types
    val name = if (isObject) classTag.runtimeClass.getName.dropRight(1) else classTag.runtimeClass.getName
    val index = name.lastIndexOf(".")
    // types nested in object have names such as 'Outer$Inner' but in code you refer to them as 'Outer.Inner'
    val value = name.substring(index + 1).replaceAll("""\$""", ".")
    RName(value)
  }

}
