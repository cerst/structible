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

package com.github.cerst.structible.pureconfig

import com.github.cerst.structible.core.{Constructible, Destructible, Structible}
import pureconfig.{ConfigConvert, ConfigReader, ConfigWriter}

object ops {

  // ===================================================================================================================
  // CONSTRUCTIBLE
  // ===================================================================================================================
  implicit class ConstructiblePureconfigOps[C, R](val constructible: Constructible[C, R]) extends AnyVal {

    def toConfigReader(implicit configReader: ConfigReader[C]): ConfigReader[R] = {
      configReader.map(constructible.construct)
    }

  }

  // ===================================================================================================================
  // DESTRUCTIBLE
  // ===================================================================================================================
  implicit class DestructiblePureconfigOps[C, R](val destructible: Destructible[C, R]) extends AnyVal {

    def toConfigWriter(implicit configWriter: ConfigWriter[C]): ConfigWriter[R] = {
      configWriter.contramap(destructible.destruct)
    }

  }

  // ===================================================================================================================
  // STRUCTIBLE
  // ===================================================================================================================
  implicit class StructiblePureconfigOps[C, R](val structible: Structible[C, R]) extends AnyVal {

    def toConfigConvert(implicit configConvert: ConfigConvert[C]): ConfigConvert[R] = {
      configConvert.xmap(structible.construct, structible.destruct)
    }

  }

}
