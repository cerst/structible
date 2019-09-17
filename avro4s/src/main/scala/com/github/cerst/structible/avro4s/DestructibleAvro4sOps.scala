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

package com.github.cerst.structible.avro4s

import com.github.cerst.structible.core.Destructible
import com.sksamuel.avro4s.{Encoder, SchemaFor}

final class DestructibleAvro4sOps[C, R](val destructible: Destructible[C, R]) extends AnyVal {

  def toEncoder(implicit cEncoder: Encoder[C]): Encoder[R] = cEncoder.comap(destructible.destruct)

  // the SchemaFor for R is the same as for C because R is serialized just like C
  // (unless we want to add custom Avro type refinements which are e.g. not supported by Schema Registry)
  def toSchemaFor(implicit cSchemeFor: SchemaFor[C]): SchemaFor[R] = cSchemeFor.map(identity)

}
