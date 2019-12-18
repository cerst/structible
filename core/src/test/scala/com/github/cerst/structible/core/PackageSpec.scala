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

import org.scalatest.Assertions
import org.scalatest.freespec.AnyFreeSpec

final class PackageSpec extends AnyFreeSpec with Assertions {

  import PackageSpec._

  "class to RName should work for top level declarations" in {
    val actual = RName[PackageSpec]
    val expected = RName("PackageSpec")

    assert(actual == expected)
  }

  "class to RName should work for nested traits" in {
    val actual = RName[NestedTrait]
    val expected = RName("PackageSpec.NestedTrait")

    assert(actual == expected)
  }

  "class to RName should work for nested classes" in {
    val actual = RName[NestedClass]
    val expected = RName("PackageSpec.NestedClass")

    assert(actual == expected)
  }

  "object to RName should work for top level declarations" in {
    val actual = objectToRName(PackageSpec)
    val expected = RName("PackageSpec")

    assert(actual == expected)
  }

  "object To RName should work for nested objects" in {
    val actual = objectToRName(NestedObject)
    val expected = RName("PackageSpec.NestedObject")

    assert(actual == expected)
  }

}

// needed for testing
object PackageSpec {

  sealed trait NestedTrait

  final class NestedClass

  object NestedObject
}
