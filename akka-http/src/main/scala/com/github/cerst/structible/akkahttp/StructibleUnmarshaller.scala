package com.github.cerst.structible.akkahttp

import akka.http.scaladsl.unmarshalling.Unmarshaller
import com.github.cerst.structible.Constructible
trait StructibleUnmarshaller {

  implicit def structibleUnmarshaller[C, R](
    implicit constructible: Constructible[C, R],
    unmarshaller: Unmarshaller[String, C]
  ): Unmarshaller[String, R] = {
    unmarshaller map constructible.constructUnsafe
  }

}

object StructibleUnmarshaller extends StructibleUnmarshaller
