package nmcb
package minio
package client

import pureconfig.*
import pureconfig.error.*
import pureconfig.generic.derivation.default.*

case class Config(endpoint: String, accessKey: String, secretKey: String) derives ConfigReader

object Config:

  import cats.*
  import cats.effect.*
  import cats.implicits.*
  import com.typesafe.config.ConfigFactory

  def resource: Resource[IO, Config] =
    val config =
      IO.delay(ConfigSource.default.at("minio-client").load[Config]).flatMap:
        case Left(error)  => IO.raiseError[Config](new ConfigReaderException[Config](error))
        case Right(value) => IO.pure(value)
    Resource.eval(config)

  def load: IO[Config] =
    resource.use(IO.pure)

