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

import scala.util.control.NonFatal

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Constructible[C, R] {

  def constructSafe(c: C): Either[String, R]

  def constructUnsafe(c: C): R

}

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Destructible[C, R] {

  def destruct(r: R): C
}

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Structible[C, R] extends Constructible[C, R] with Destructible[C, R]

object Structible {

  def apply[P, W](implicit structible: Structible[P, W]): Structible[P, W] =
    structible

  /**
    * Creates a structible based on the provided functions.<br/>
    * <i>constructSafe</i> is derived using <i>try constructUnsafe catch</i>
    */
  def instanceUnsafe[C, R](constrUnsafe: C => R,
                           destr: R => C): Structible[C, R] = {

    new Structible[C, R] {

      override def destruct(r: R): C = destr(r)

      override def constructSafe(c: C): Either[String, R] = {
        try {
          Right(constrUnsafe(c))
        } catch {
          case NonFatal(cause) =>
            Left(cause.getMessage)
        }
      }

      override def constructUnsafe(c: C): R = constrUnsafe(c)

    }
  }

  /**
    * Creates a structible based on the provided functions.<br/>
    * <i>constructUnsafe</i> is derived by throwing an <i>IllegalArgumentException</i> in case the former yields <i>Left</i>.
    */
  def instanceSafe[C, R](constrSafe: C => Either[String, R],
                         destr: R => C): Structible[C, R] = {

    new Structible[C, R] {
      override def destruct(r: R): C = destr(r)

      override def constructSafe(c: C): Either[String, R] = constrSafe(c)

      override def constructUnsafe(c: C): R = {
        constrSafe(c) fold (
          error => throw new IllegalArgumentException(error),
          identity
        )
      }
    }
  }

}
