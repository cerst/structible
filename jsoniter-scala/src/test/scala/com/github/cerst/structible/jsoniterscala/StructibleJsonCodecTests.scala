package com.github.cerst.structible.jsoniterscala

import com.github.cerst.structible.Structible
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import utest._

object StructibleJsonCodecTests extends TestSuite {

  final case class Device(id: Device.Id, model: String)

  object Device {

    final case class Id(value: Int) {
      require(value >= 0)
    }

    object Id {

      implicit val structibleForDeviceId: Structible[Int, Id] =
        Structible.instanceUnsafe(apply, _.value)

      implicit val jsonCodecForDeviceId: JsonValueCodec[Id] =
        StructibleJsonCodec.int
    }

  }

  final case class User(name: User.Name, devices: Seq[Device])

  object User {

    final case class Name(value: String) {
      require(value.nonEmpty)
    }

    object Name {
      implicit val structibleForUserName: Structible[String, Name] =
        Structible.instanceUnsafe(apply, _.value)

      implicit val jsonCodecForUsername: JsonCodec[Name] =
        StructibleJsonCodec.string
    }

  }

  override val tests: Tests = Tests {

    * - {
      implicit val codec: JsonValueCodec[User] =
        JsonCodecMaker.make[User](CodecMakerConfig())

      val json = writeToArray(
        User(
          name = User.Name("John"),
          devices = Seq(Device(id = Device.Id(2), model = "iPhone X"))
        )
      )

      println(new String(json, "UTF-8"))

      val user = readFromArray[User](
        """{"name":"John","devices":[{"id":1,"model":"HTC One X"}]}"""
          .getBytes("UTF-8")
      )

      println(user)
    }

  }
}
