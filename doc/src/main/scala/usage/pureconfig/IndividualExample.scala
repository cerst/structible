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

package usage.pureconfig

// #example
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._
import com.github.cerst.structible.pureconfig.ops._
import com.typesafe.config.{Config, ConfigValue}
import pureconfig.{ConfigReader, ConfigSource, ConfigWriter}

object IndividualExample {

  final class UserId private(val value: Long) extends AnyVal

  object UserId {

    private val structible: Structible[Long, UserId] = Structible.structible(new UserId(_), _.value, c >= 0)

    implicit val configReaderForUserId: ConfigReader[UserId] = structible.toConfigReader

    implicit val configWriterForUserId: ConfigWriter[UserId] = structible.toConfigWriter

    def apply(value: Long): UserId = structible.construct(value)
  }

  def fromConfig(config: Config): ConfigReader.Result[UserId] = {
    ConfigSource.fromConfig(config).load[UserId]
  }

  def toConfigValue(personId: UserId): ConfigValue = {
    ConfigWriter[UserId].to(personId)
  }

}
// #example
