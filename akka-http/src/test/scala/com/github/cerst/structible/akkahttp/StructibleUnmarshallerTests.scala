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

package com.github.cerst.structible.akkahttp

import akka.actor.ActorSystem
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.testkit.TestKit
import StructibleUnmarshaller._
import com.github.cerst.structible.Structible
import utest._

object StructibleUnmarshallerTests extends TestSuite {

  final case class RefinedId(value: Int)

  object RefinedId {

    implicit val structibleForRefinedId: Structible[Int, RefinedId] =
      Structible.instanceUnsafe(apply, _.value)
  }

  implicit val actorSystem: ActorSystem = ActorSystem(
    "StructibleUnmarshallerTests"
  )
  implicit val materializer: Materializer = ActorMaterializer()

  override def utestAfterAll(): Unit = {
    TestKit.shutdownActorSystem(actorSystem)
    super.utestAfterAll()
  }

  override val tests: Tests = Tests {

    * - {
      import actorSystem.dispatcher

      Unmarshal("1").to[RefinedId] map (value => assert(value == RefinedId(1)))
    }
  }

}
