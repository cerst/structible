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

package com.github.cerst.structible.akkahttp

import java.util.UUID

import akka.http.scaladsl.server.PathMatcher1
import akka.http.scaladsl.server.PathMatchers._
import com.github.cerst.structible.core.Constructible

object StructiblePathMatcher {

  // A path-matcher method throwing yields a 500 response code rather than not matching the path
  // None has to be returned to express the latter
  // Thus, the code below it set-up to call constructSafe explicitly everywhere
  private implicit class EitherMap[C](val pathMatcher1C: PathMatcher1[C]) extends AnyVal {
    def rightMap[R](f: C => Either[String, R]): PathMatcher1[R] = {
      pathMatcher1C flatMap (c => f(c).toOption)
    }
  }

  def doubleNumber[R](implicit constructible: Constructible[Double, R]): PathMatcher1[R] = {
    DoubleNumber rightMap constructible.constructSafe
  }

  def hexIntNumber[R](implicit constructible: Constructible[Int, R]): PathMatcher1[R] = {
    HexIntNumber rightMap constructible.constructSafe
  }

  def hexLongNumber[R](implicit constructible: Constructible[Long, R]): PathMatcher1[R] = {
    HexLongNumber rightMap constructible.constructSafe
  }

  def intNumber[R](implicit constructible: Constructible[Int, R]): PathMatcher1[R] =
    IntNumber rightMap constructible.constructSafe

  def longNumber[R](implicit constructible: Constructible[Long, R]): PathMatcher1[R] = {
    LongNumber rightMap constructible.constructSafe
  }

  def segment[R](implicit constructible: Constructible[String, R]): PathMatcher1[R] = {
    Segment rightMap constructible.constructSafe
  }

  def javaUUID[R](implicit constructible: Constructible[UUID, R]): PathMatcher1[R] = {
    JavaUUID rightMap constructible.constructSafe
  }

}
