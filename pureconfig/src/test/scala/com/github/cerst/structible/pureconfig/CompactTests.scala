package com.github.cerst.structible.pureconfig

import com.github.cerst.structible.core.Structible
import com.github.cerst.structible.pureconfig.ops._
import com.typesafe.config._
import pureconfig._
import pureconfig.generic.semiauto._
import utest._

object CompactTests extends TestSuite {

  final case class DeviceId private (value: Int) extends AnyVal

  object DeviceId {

    private val structible: Structible[Int, DeviceId] = Structible.structible(apply, _.value)

    implicit val configConvertForDeviceId: ConfigConvert[DeviceId] = structible.toConfigConvert

    def apply(value: Int): DeviceId = {
      require(value >= 0)
      new DeviceId(value)
    }

  }

  final case class Device(id: DeviceId, model: String)

  object Device {
    implicit val configConvertForDevice: ConfigConvert[Device] = deriveConvert[Device]
  }

  final case class Username private (value: String) extends AnyVal

  object Username {

    private val structible: Structible[String, Username] = Structible.structible(apply, _.value)

    implicit val configConvertForUsername: ConfigConvert[Username] = structible.toConfigConvert

    def apply(value: String): Username = {
      require(value.nonEmpty)
      new Username(value)
    }

  }

  final case class User(name: Username, devices: Seq[Device])

  object User {
    implicit val configConvertForUser: ConfigConvert[User] = deriveConvert[User]
  }

  val config: Config = ConfigFactory.parseString("""{
                                                   |  devices = [
                                                   |    {
                                                   |      id = 1
                                                   |      model = HTC One X
                                                   |    }
                                                   |  ]
                                                   |  name = John
                                                   |}
                                                   |""".stripMargin)

  val user = User(Username("John"), devices = Seq(Device(DeviceId(1), "HTC One X")))

  override val tests: Tests = Tests {

    test("read config") {
      val actualUser = ConfigSource.fromConfig(config).loadOrThrow[User]
      assert(actualUser == user)
    }

    test("write config") {
      // couldn't find a way to get from ConfigValue to Config with ".render" ....
      val actualConfig = ConfigFactory parseString ConfigWriter[User].to(user).render
      assert(actualConfig == config)
    }

  }
}