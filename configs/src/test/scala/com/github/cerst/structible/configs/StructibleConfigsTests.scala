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

package com.github.cerst.structible.configs

import com.github.cerst.structible.Structible
import com.typesafe.config.ConfigFactory
import configs.Configs
import configs.syntax._
import utest._

sealed trait Random

final case class Device(id: Device.Id, model: String)

object Device {

  final case class Id(value: Int) {
    require(value >= 0)
  }

  object Id {
    implicit val structibleForDeviceId: Structible[Int, Id] =
      Structible.instanceUnsafe(apply, _.value)

    implicit val configsForDeviceId: Configs[Id] = StructibleConfigs[Int, Id]
  }

}

final case class User(name: User.Name, devices: Seq[Device])

object User {

  final case class Name(value: String) {
    require(value.nonEmpty)
  }

  object Name {
    implicit val structibleForUserName: Structible[String, Name] =
      Structible.instanceUnsafe(apply, _.value)

    implicit val configsForUserName: Configs[Name] = StructibleConfigs[String, Name]
  }

}

object StructibleConfigsTests extends TestSuite {

  override val tests: Tests = Tests {

    * - {
      val config = ConfigFactory.parseString("""{
          |  user {
          |    devices = [
          |      {
          |        id = 1
          |        model = HTC One X
          |      }
          |    ]
          |    name = John
          |  }
          |}
        """.stripMargin)

      val user = config.get[User]("user").value
      println(user)
    }

  }

}
