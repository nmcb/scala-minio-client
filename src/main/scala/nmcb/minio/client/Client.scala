package nmcb
package minio
package client

import io.minio.BucketExistsArgs

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

  def load: Resource[IO, Client] =
    Config.load.map: config =>
      Client(config)