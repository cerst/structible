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

package usage.configs

// #example
import com.github.cerst.structible.configs.ops._
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._
import com.typesafe.config.Config
import configs._
import configs.syntax._

object ConfigsExample {

  final case class UserId private (value: Long) extends AnyVal

  object UserId {

    private val structible: Structible[Long, UserId] = Structible.structible(new UserId(_), _.value, c >= 0)

    implicit val configsForUserId: Configs[UserId] = structible.toConfigs

    def apply(value: Long): UserId = structible.construct(value)
  }

  def readFromConfig(config: Config, path: String): Result[UserId] = {
    config.get[UserId](path)
  }

}
// #example
