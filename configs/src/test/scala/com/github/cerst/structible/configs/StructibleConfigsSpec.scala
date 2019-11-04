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

import com.github.cerst.structible.configs.ops._
import com.github.cerst.structible.core._
import com.typesafe.config.ConfigFactory
import configs.Configs
import configs.syntax._
import org.scalatest.{FreeSpec, Matchers}

final class StructibleConfigsSpec extends FreeSpec with Matchers {

  import StructibleConfigsSpec._

  "read config" in {
    val config = ConfigFactory.parseString("""{
          |  user {
          |    id = 1
          |  }
          |}
        """.stripMargin)

    val actualUser = config.get[User]("user").value
    val expectedUser = User(UserId(1))
    assert(actualUser == expectedUser)
  }

}

private object StructibleConfigsSpec {

  final case class UserId private (value: Int) extends AnyVal

  object UserId {

    private val structible: Structible[Int, UserId] =
      Structible.structible(new UserId(_), _.value, c.unconstrained)

    implicit val configsForDeviceId: Configs[UserId] = structible.toConfigs

    def apply(value: Int): UserId = structible.construct(value)

  }

  final case class User(id: UserId)

}
