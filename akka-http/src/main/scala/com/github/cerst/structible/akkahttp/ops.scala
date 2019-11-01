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
import akka.http.scaladsl.unmarshalling.Unmarshaller
import com.github.cerst.structible.core.Constructible

object ops {

  implicit class ConstructibleAkkaHttpOps[C, R](val constructible: Constructible[C, R]) extends AnyVal {

    def toUnmarshaller(implicit unmarshaller: Unmarshaller[String, C]): Unmarshaller[String, R] = {
      unmarshaller map constructible.construct
    }

  }

  // Akka Http PathMatchers are defined and used explicitly - so, the derivations have to be the same

  // ===================================================================================================================
  // DOUBLE
  // ===================================================================================================================
  implicit class ConstructibleAkkaHttpDoubleOps[R](val constructible: Constructible[Double, R]) extends AnyVal {

    def toPathMatcher: PathMatcher1[R] = {
      DoubleNumber flatMap (Double => constructible.constructOption(Double))
    }

  }

  // ===================================================================================================================
  // INT
  // ===================================================================================================================
  implicit class ConstructibleAkkaHttpIntOps[R](val constructible: Constructible[Int, R]) extends AnyVal {

    def toHexPathMatcher: PathMatcher1[R] = {
      HexIntNumber flatMap (hexInt => constructible.constructOption(hexInt))
    }

    def toPathMatcher: PathMatcher1[R] = {
      IntNumber flatMap (int => constructible.constructOption(int))
    }

  }

  // ===================================================================================================================
  // LONG
  // ===================================================================================================================
  implicit class ConstructibleAkkaHttpLongOps[R](val constructible: Constructible[Long, R]) extends AnyVal {

    def toHexPathMatcher: PathMatcher1[R] = {
      HexLongNumber flatMap (hexLong => constructible.constructOption(hexLong))
    }

    def toPathMatcher: PathMatcher1[R] = {
      LongNumber flatMap (long => constructible.constructOption(long))
    }

  }

  // ===================================================================================================================
  // STRING
  // ===================================================================================================================
  implicit class ConstructibleAkkaHttpStringOps[R](val constructible: Constructible[String, R]) extends AnyVal {

    def toPathMatcher: PathMatcher1[R] = {
      Segment flatMap (string => constructible.constructOption(string))
    }

  }

  // ===================================================================================================================
  // UUID
  // ===================================================================================================================
  implicit class ConstructibleAkkaHttpUuidOps[R](val constructible: Constructible[UUID, R]) extends AnyVal {

    def toPathMatcher: PathMatcher1[R] = {
      JavaUUID flatMap (uuid => constructible.constructOption(uuid))
    }

  }

}
