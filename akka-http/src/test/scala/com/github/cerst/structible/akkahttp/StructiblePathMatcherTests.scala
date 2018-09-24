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

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatcher1
import com.github.cerst.structible.akkahttp.testutil._
import utest._

object StructiblePathMatcherTests extends RouteTestSuite {

  private def test[R](pathMatcher1: PathMatcher1[R], matchable: (String, R), unmatchable: String): Unit = {
    val route = path(pathMatcher1) { refined =>
      validate(refined == matchable._2, "PathMatcher yielded unexpected value") {
        complete(StatusCodes.OK)
      }
    }

    Get(s"/${matchable._1}") ~> route ~> check {
      assert(status == StatusCodes.OK)
    }

    Get(s"/$unmatchable") ~> route ~> check {
      assert(!handled)
    }
  }

  override val tests: Tests = Tests {

    "double" - {
      test(NegDouble.pm, "-0.3" -> NegDouble(-0.3D), "0.3")
    }

    "int from hex-string" - {
      test(OddInt.hexIntPm, "b" -> OddInt(11), "a")
    }

    "int from string" - {
      test(OddInt.intPm, "1" -> OddInt(1), "0")
    }

    "long from hex-string" - {
      test(PosLong.hexLongPm, "f" -> PosLong(15), "0")
    }

    "long from string" - {
      test(PosLong.longPm, "1" -> PosLong(1), "0")
    }

    "string" - {
      test(NonEmptyString.pm, "a" -> NonEmptyString("a"), "")
    }

  }
}
