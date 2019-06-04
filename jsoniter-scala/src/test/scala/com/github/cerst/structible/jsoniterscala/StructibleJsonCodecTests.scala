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

package com.github.cerst.structible.jsoniterscala

import java.nio.charset.StandardCharsets.UTF_8
import java.time.{Duration, OffsetDateTime, ZonedDateTime}
import java.util.UUID

import com.github.cerst.structible.jsoniterscala.testutil._
import com.github.plokhotnyuk.jsoniter_scala.core._
import utest._

object StructibleJsonCodecTests extends TestSuite {

  private val wrapper = Wrapper(
    BigDecimalValueClass(BigDecimal(200.0)),
    BigIntValueClass(BigInt(200)),
    BooleanValueClass(true),
    DoubleValueClass(5.4),
    DurationValueClass(Duration.ofSeconds(50, 399)),
    FloatValueClass(10.45F),
    IntValueClass(48),
    LongValueClass(1234L),
    OffsetDateTimeValueClass(OffsetDateTime parse "2019-05-29T19:49:35.814+02:00"),
    ShortValueClass(7),
    StringValueClass("Hello World"),
    UUIDValueClass(UUID fromString "c1df0070-4ccc-431c-a634-378c90624620"),
    ZonedDateTimeValueClass(ZonedDateTime parse "2019-05-29T19:50:54.008+02:00[Europe/Berlin]")
  )

  private val jsonBytes =
    """{
      |"bigDecimalValueClass":200.0,
      |"bigIntValueClass":200,
      |"booleanValueClass":true,
      |"doubleValueClass":5.4,
      |"durationValueClass":"PT50.000000399S",
      |"floatValueClass":10.45,
      |"intValueClass":48,
      |"longValueClass":1234,
      |"offsetDateTimeValueClass":"2019-05-29T19:49:35.814+02:00",
      |"shortValueClass":7,
      |"stringValueClass":"Hello World",
      |"uuidValueClass":"c1df0070-4ccc-431c-a634-378c90624620",
      |"zonedDateTimeValueClass":"2019-05-29T19:50:54.008+02:00[Europe/Berlin]"
      |}""".stripMargin.replaceAll("\n", "") getBytes UTF_8

  override val tests: Tests = Tests {

    "to Json bytes" - {
      val actualJson = writeToArray(wrapper)
      assert(actualJson sameElements jsonBytes)
    }

    "from Json bytes" - {
      val actualUser = readFromArray[Wrapper](jsonBytes)
      assert(actualUser == wrapper)
    }

  }

}
