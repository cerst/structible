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

import com.github.cerst.structible.core.Structible
import com.github.cerst.structible.jsoniterscala.ops._
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import utest._

object StructibleJsonCodecTests extends TestSuite {

  final case class Device(id: Device.Id, model: String)

  object Device {

    final case class Id(value: Int) {
      require(value >= 0)
    }

    object Id {
      private val structible: Structible[Int, Id] = Structible.instanceUnsafe(apply, _.value)

      implicit val jsonCodecForDeviceId: JsonValueCodec[Id] = structible.toJsonCodec
    }

  }

  final case class User(name: User.Name, devices: Seq[Device])

  object User {

    final case class Name(value: String) {
      require(value.nonEmpty)
    }

    object Name {
      private val structible: Structible[String, Name] = Structible.instanceUnsafe(apply, _.value)

      implicit val jsonCodecForUsername: JsonCodec[Name] = structible.toJsonCodec
    }

  }

  override val tests: Tests = Tests {

    implicit val codec: JsonValueCodec[User] = JsonCodecMaker.make[User](CodecMakerConfig())

    val jsonBytes = """{"name":"John","devices":[{"id":1,"model":"iPhone X"}]}""" getBytes UTF_8
    val user = User(name = User.Name("John"), devices = Seq(Device(id = Device.Id(1), model = "iPhone X")))

    "to Json bytes" - {
      val actualJson = writeToArray(user)
      assert(actualJson sameElements jsonBytes)
    }

    "from Json bytes" - {
      val actualUser = readFromArray(jsonBytes)
      assert(actualUser == user)
    }

  }

}
