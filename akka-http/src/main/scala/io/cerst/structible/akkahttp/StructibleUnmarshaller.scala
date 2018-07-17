package io.cerst.structible.akkahttp

import akka.http.scaladsl.unmarshalling.Unmarshaller
import io.cerst.structible.Constructible

trait StructibleUnmarshaller {

  implicit def structibleUnmarshaller[C, R](
    implicit constructible: Constructible[C, R],
    unmarshaller: Unmarshaller[String, C]
  ): Unmarshaller[String, R] = {
    unmarshaller map constructible.constructUnsafe
  }

}

object StructibleUnmarshaller extends StructibleUnmarshaller
