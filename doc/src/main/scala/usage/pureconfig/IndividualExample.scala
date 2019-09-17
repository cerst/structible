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
import com.github.cerst.structible.core.Structible
import com.github.cerst.structible.pureconfig.ops._
import com.typesafe.config.{Config, ConfigValue}
import pureconfig.{ConfigReader, ConfigSource, ConfigWriter}

object IndividualExample {

  final case class PersonId(value: Long) {
    require(value >= 0, s"PersonId must be non-negative (got: '$value')")
  }

  object PersonId {

    // you can also pass-in 'construct' functions returning Either[String, A], Option[A] or Try[A]
    private val structible: Structible[Long, PersonId] = Structible.structible(PersonId.apply, _.value)

    implicit val configReaderForPersonId: ConfigReader[PersonId] = structible.toConfigReader

    implicit val configWriterForPersonId: ConfigWriter[PersonId] = structible.toConfigWriter

  }

  def fromConfig(config: Config): ConfigReader.Result[PersonId] = {
    ConfigSource.fromConfig(config).load[PersonId]
  }

  def toConfigValue(personId: PersonId): ConfigValue = {
    ConfigWriter[PersonId].to(personId)
  }

}
// #example