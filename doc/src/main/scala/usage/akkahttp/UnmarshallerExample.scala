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

package usage.akkahttp

// #example
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import com.github.cerst.structible.akkahttp.ops._
import com.github.cerst.structible.core.DefaultConstraints._
import com.github.cerst.structible.core._

import scala.concurrent.{ExecutionContext, Future}

object UnmarshallerExample {

  final case class ItemId private (value: Long) extends AnyVal

  object ItemId {

    // you can also pass-in 'construct' functions returning Either[String, A], Option[A] or Try[A]
    private val structible: Structible[Long, ItemId] =
      Structible.structible(new ItemId(_), _.value, c >= 0, hideC = false)

    implicit val unmarshallerForPersonId: Unmarshaller[String, ItemId] = structible.toUnmarshaller

  }

  // this works seamlessly for e.g. HttpResponse.entity instead of String
  def toPersonId(input: String)(implicit ec: ExecutionContext, mat: Materializer): Future[ItemId] = {
    Unmarshal(input).to[ItemId]
  }

}
// #example
