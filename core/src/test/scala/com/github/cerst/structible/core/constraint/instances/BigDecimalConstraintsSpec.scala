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

import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core.constraint.instances.BigDecimalConstraintsSpec.chooseForBigDecimal
import com.github.cerst.structible.core.testutil.NumericConstraintsSpec
import org.scalacheck.Gen
import org.scalacheck.Gen.Choose

final class BigDecimalConstraintsSpec
    extends NumericConstraintsSpec[BigDecimal](
      dec = x => BigDecimal(Math nextDown x.doubleValue),
      inc = x => BigDecimal(Math nextUp x.doubleValue),
      globalMax = BigDecimal(Double.MaxValue),
      globalMin = BigDecimal(Double.MinValue)
    ) {}

object BigDecimalConstraintsSpec {

  implicit final val chooseForBigDecimal: Choose[BigDecimal] = {
    Gen.Choose.xmap[Double, BigDecimal](BigDecimal.apply, _.toDouble)
  }

}
