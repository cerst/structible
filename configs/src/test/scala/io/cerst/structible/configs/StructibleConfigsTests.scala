package io.cerst.structible.configs

import com.typesafe.config.ConfigFactory
import configs.Configs
import configs.syntax._
import io.cerst.structible.Structible
import utest._

sealed trait Random

final case class Device(id: Device.Id, model: String)

object Device {

  final case class Id(value: Int) {
    require(value >= 0)
  }

  object Id {
    implicit val structibleForDeviceId: Structible[Int, Id] =
      Structible.instanceUnsafe(apply, _.value)

    implicit val configsForDeviceId: Configs[Id] = StructibleConfigs[Int, Id]
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

    implicit val configsForUserName: Configs[Name] = StructibleConfigs[String, Name]
  }

}

object StructibleConfigsTests extends TestSuite {

  override val tests: Tests = Tests {

    * - {
      val config = ConfigFactory.parseString("""{
          |  user {
          |    devices = [
          |      {
          |        id = 1
          |        model = HTC One X
          |      }
          |    ]
          |    name = John
          |  }
          |}
        """.stripMargin)

      val user = config.get[User]("user").value
      println(user)
    }

  }

}
