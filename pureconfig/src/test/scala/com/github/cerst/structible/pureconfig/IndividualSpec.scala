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

import com.github.cerst.structible.core._
import com.github.cerst.structible.pureconfig.ops._
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FreeSpec, Matchers}
import pureconfig.generic.semiauto._
import pureconfig.{ConfigReader, ConfigWriter, _}

final class IndividualSpec extends FreeSpec with Matchers {

  import IndividualSpec._

  "read config" in {
    val actualUser = ConfigSource.fromConfig(config).loadOrThrow[User]
    assert(actualUser == user)
  }

  "write config" in {
    // couldn't find a way to get from ConfigValue to Config with ".render" ....
    val actualConfig = ConfigFactory parseString ConfigWriter[User].to(user).render
    assert(actualConfig == config)
  }

}

private object IndividualSpec {

  final class UserId private (val value: Int) extends AnyVal

  object UserId {

    private val structible: Structible[Int, UserId] = Structible.structible(new UserId(_), _.value, c.unconstrained)

    implicit val configReaderForDeviceId: ConfigReader[UserId] = structible.toConfigReader

    implicit val configWriterForDeviceId: ConfigWriter[UserId] = structible.toConfigWriter

    def apply(value: Int): UserId = structible.construct(value)

  }

  final case class User(id: UserId)

  object User {
    implicit val configReaderForUser: ConfigReader[User] = deriveReader[User]
    implicit val configWriterForUser: ConfigWriter[User] = deriveWriter[User]
  }

  val config: Config = ConfigFactory.parseString("""{
                                                   |  id = 1
                                                   |}
                                                   |""".stripMargin)

  val user = User(UserId(1))
}
