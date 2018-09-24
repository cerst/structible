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

import scala.util.control.NonFatal

/**
  * Captures a problem raised when calling [[com.github.cerst.structible.core.Constructible#constructUnsafe Constructible.constructUnsafe]]
  */
final class ConstructException private (cause: Option[Throwable], message: Option[String])
    extends Exception(message.orNull, cause.orNull)

object ConstructException {
  def apply(cause: Option[Throwable], message: Option[String]): ConstructException = {
    new ConstructException(cause, message)
  }
}

/**
  * @tparam C Common type
  * @tparam R Refined type
  */
trait Constructible[C, R] {

  def constructSafe(c: C): Either[String, R]

  @throws[ConstructException]
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
    * Creates a structible based on the provided functions.
    *
    * @param constrUnsafe If this throws,
    *                     <ul>
    *                       <li><i>constructSafe</i> returns a [[scala.util.Left$ Left]] containing the message of the exception</li>
    *                       <li><i>constructUnsafe</i> re-throws any non-fatal exception wrapped in a [[com.github.cerst.structible.core.ConstructException ConstructException]]</li>
    *                     </ul>
    *
    */
  // parameter names have been abbreviated to not conflict with method names below (which would set-up a recursive call)
  def instanceUnsafe[C, R](constrUnsafe: C => R, destr: R => C): Structible[C, R] = {

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

      override def constructUnsafe(c: C): R = {
        try {
          constrUnsafe(c)
        } catch {
          case NonFatal(cause) =>
            throw ConstructException(Some(cause), None)
        }
      }

    }
  }

  /**
    * Creates a structible based on the provided functions.
    *
    * @param constrSafe If this returns [[scala.util.Left$ Left]],
    *                   <ul>
    *                     <li><i>constructUnsafe</i> throws a [[com.github.cerst.structible.core.ConstructException ConstructException]] having the value of the former as its <i>message</i></li>
    *                   </ul>
    */
  // parameter names have been abbreviated to not conflict with method names below (which would set-up a recursive call)
  def instanceSafe[C, R](constrSafe: C => Either[String, R], destr: R => C): Structible[C, R] = {

    new Structible[C, R] {
      override def destruct(r: R): C = destr(r)

      override def constructSafe(c: C): Either[String, R] = constrSafe(c)

      override def constructUnsafe(c: C): R = {
        constrSafe(c) fold (
          error => throw ConstructException(None, Some(error)),
          identity
        )
      }
    }
  }

}
