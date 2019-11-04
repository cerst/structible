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

package com.github.cerst.structible.core.internal

import com.github.cerst.structible.core.RName

import scala.util.{Failure, Try}

object Error {

  def failure[C, R](c: C, rName: RName, cause: Throwable): Try[R] = {
    val msg = errorMsg(c, rName, reason = getMessageNonNull(cause))
    Failure(new RuntimeException(msg, cause))
  }

  def failure[C, R](c: C, rName: RName, reason: String = ""): Try[R] = {
    val msg = errorMsg(c, rName, reason)
    Failure(new RuntimeException(msg))
  }

  def left[C, R](c: C, rName: RName, cause: Throwable): Either[String, R] = {
    val msg = errorMsg(c, rName, reason = getMessageNonNull(cause))
    Left(msg)
  }

  def left[C, R](c: C, rName: RName, reason: String = ""): Either[String, R] = {
    val msg = errorMsg(c, rName, reason)
    Left(msg)
  }

  def throwException[C, R](c: C, rName: RName, reason: String): Nothing = {
    val msg = errorMsg(c, rName, reason)
    throw new IllegalArgumentException(msg)
  }

  def throwException[C, R](c: C, rName: RName, cause: Throwable): Nothing = {
    val msg = errorMsg(c, rName, reason = "")
    throw new IllegalArgumentException(msg, cause)
  }

  private def errorMsg[C, R](c: C, rName: RName, reason: String): String = {
    val suffix = if (reason.nonEmpty) s" due to: $reason" else ""
    s"Failed to construct '${rName.value}' from '$c' $suffix"
  }

  /**
    * Safe way to access [[java.lang.Throwable#getMessage()]] which may return null.
    */
  private def getMessageNonNull(throwable: Throwable): String = {
    if (throwable.getMessage == null) "<exception.getMessage was null>" else throwable.getMessage
  }

}
