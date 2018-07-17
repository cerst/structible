package io.cerst.structible.akkahttp

import akka.actor.ActorSystem
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.testkit.TestKit
import io.cerst.structible.Structible
import io.cerst.structible.akkahttp.StructibleUnmarshaller._
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
