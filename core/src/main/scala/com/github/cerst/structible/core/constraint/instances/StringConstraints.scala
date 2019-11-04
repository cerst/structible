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

package com.github.cerst.structible.core.constraint.instances

import com.github.cerst.structible.core.constraint.syntax._

trait StringConstraints {

  implicit val lengthLessThanForString: LengthLessThan[String] = x => { y =>
    if (y.lengthCompare(x) < 0) List.empty else List(s"_.length < $x")
  }

  implicit val lengthLessThanOrEqualForString: LengthLessThanOrEqual[String] = x => { y =>
    if (y.lengthCompare(x) <= 0) List.empty else List(s"_.length <= $x")
  }

  implicit val lengthGreaterThanForString: LengthGreaterThan[String] = x => { y =>
    if (y.lengthCompare(x) > 0) List.empty else List(s"_.length > $x")
  }

  implicit val lengthGreaterThanOrEqualForString: LengthGreaterThanOrEqual[String] = x => { y =>
    if (y.lengthCompare(x) >= 0) List.empty else List(s"_.length >= $x")
  }

  implicit val matchesForString: Matches[String] = x => { y =>
    if (x.pattern.matcher(y).matches()) List.empty else List(s"_ matches $x")
  }

  implicit val nonEmptyForString: NonEmpty[String] = _ => { y =>
    if (y.nonEmpty) List.empty else List("_.nonEmpty")
  }

}

object StringConstraints extends StringConstraints
