package nmcb
package minio
package client

import io.minio.{BucketExistsArgs, MakeBucketArgs}

class Client(config: Config):

  import io.minio.MinioClient

  lazy val underlying: MinioClient =
    MinioClient
      .builder()
      .endpoint(config.endpoint)
      .credentials(config.accessKey, config.secretKey)
      .build()

  def bucketExists(bucket: String): Boolean =
    underlying.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())

object Client:

  import cats.*
  import cats.effect.*
  import cats.implicits.*

  def resource: Resource[IO, Client] =
    Config.resource.map: config =>
      Client(config)

  def resourceWith(config: Config): Resource[IO, Client] =
    Resource.eval(IO.pure(Client(config)))
