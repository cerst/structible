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

import com.github.cerst.structible.core.DefaultConstraints._
import org.scalatest.Assertions
import org.scalatest.freespec.AnyFreeSpec

final class ConstraintSpec extends AnyFreeSpec with Assertions {

  "&& fails if this fails" in {
    val constraint = c > 0 && c < 10
    assertResult(List("_ > 0"))(constraint(0))
  }

  "&& fails if that fails" in {
    val constraint = c > 0 && c < 10
    assertResult(List("_ < 10"))(constraint(10))
  }

  "&& fails if this and that fail" in {
    val constraint = c < 10 && c < 100
    assertResult(List("_ < 10", "_ < 100"))(constraint(101))
  }

  "&& succeeds if this and that succeed" in {
    val constraint = c > 0 && c < 10
    assertResult(List.empty)(constraint(7))
  }

  "|| fails if this and that fail" in {
    val constraint = c < 10 || c < 100
    assertResult(List("_ < 10", "_ < 100"))(constraint(101))
  }

  "|| succeeds if this succeeds" in {
    val constraint = c < 0 || c > 10
    assertResult(List.empty)(constraint(-1))
  }

  "|| succeeds if that succeeds" in {
    val constraint = c < 0 || c > 10
    assertResult(List.empty)(constraint(11))
  }

}
