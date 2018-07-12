package io.cerst.structible.jsoniterscala

import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import enumeratum._
import io.cerst.structible.Structible
import utest._

import scala.collection.immutable

sealed trait Gender extends EnumEntry

object Gender extends Enum[Gender] {

  case object Male extends Gender

  case object Female extends Gender

  override def values: immutable.IndexedSeq[Gender] = findValues

  implicit val structibleForGender: Structible[String, Gender] =
    Structible(withName, _.entryName)

  implicit val codecForGender: JsonCodec[Gender] = StructibleCodec.string
}

final case class DeviceId(value: Int)

object DeviceId {

  implicit val structibleForDeviceId: Structible[Int, DeviceId] =
    Structible(apply, _.value)

  implicit val codecForDeviceId: JsonValueCodec[DeviceId] = StructibleCodec.int
}

final case class Username(value: String)

object Username {

  implicit val structibleForUserName: Structible[String, Username] =
    Structible(apply, _.value)

  implicit val codecForUsername: JsonCodec[Username] = StructibleCodec.string
}

final case class Device(id: DeviceId, model: String)

final case class User(name: Username, devices: Seq[Device], gender: Gender)

object StructibleCodecTests extends TestSuite {

  override val tests: Tests = Tests {

    * - {
      implicit val codec: JsonValueCodec[User] =
        JsonCodecMaker.make[User](CodecMakerConfig())

      val json = writeToArray(
        User(
          name = Username("John"),
          devices = Seq(Device(id = DeviceId(2), model = "iPhone X")),
          gender = Gender.Male
        )
      )

      println(new String(json, "UTF-8"))

      val user = readFromArray[User](
        """{"name":"John","devices":[{"id":-1,"model":"HTC One X"}],"gender":"Male"}"""
          .getBytes("UTF-8")
      )

      println(user)
    }

  }
}
