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

import com.github.cerst.structible.core.Structible
import com.typesafe.config.ConfigFactory
import configs.Configs
import configs.syntax._
import utest._

final case class Device(id: DeviceId, model: String)

final case class DeviceId private (value: Int) extends AnyVal

object DeviceId {

  implicit val structibleForDeviceId: Structible[Int, DeviceId] = Structible.instanceUnsafe(apply, _.value)

  implicit val configsForDeviceId: Configs[DeviceId] = StructibleConfigs[Int, DeviceId]

  def apply(value: Int): DeviceId = {
    require(value >= 0)
    new DeviceId(value)
  }

}

final case class UserName private (value: String) extends AnyVal

object UserName {

  implicit val structibleForUserName: Structible[String, UserName] = Structible.instanceUnsafe(apply, _.value)

  implicit val configsForUserName: Configs[UserName] = StructibleConfigs[String, UserName]

  def apply(value: String): UserName = {
    require(value.nonEmpty)
    new UserName(value)
  }

}

final case class User(name: UserName, devices: Seq[Device])

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

      val actualUser = config.get[User]("user").value
      val expectedUser = User(name = UserName("John"), devices = Seq(Device(DeviceId(1), "HTC One X")))
      assert(actualUser == expectedUser)
    }

  }

}
