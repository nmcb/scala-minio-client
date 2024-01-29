package nmcb
package minio
package client

import cats.*
import cats.effect.*
import cats.implicits.*

import org.scalatest.*
import org.scalatest.concurrent.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*
import org.scalatest.time.*

import com.dimafeng.testcontainers.*

class TestMinioClient extends AsyncFlatSpec
  with Checkpoints
  with Matchers
  with BeforeAndAfterAll
  with Eventually:

  var config: Config         = null
  var minio: MinIOContainer  = null
  var client: Client         = null
  var shutdown: IO[Unit]     = null

  import cats.effect.unsafe.implicits.global

  override def beforeAll(): Unit =
    // TODO clean-up:
    // - update config dynamically based on test container (s3URL)
    // - start test-container based on config (accessKey, secretKey)
    // - proper shutdown hook for client and server
    // - programmable shutdown of the underlying okhttp client
    // - optionally remove the unsafeRunSync's from beforeAll and afterAll
    // - optionally factor into test trait
    // - optionally factor/split into integration tests
    config = Config.load.unsafeRunSync()
    minio = MinIOContainer(userName = config.accessKey, password = config.secretKey)
    minio.start()
    config = config.copy(endpoint = minio.s3URL)
    val startup = Client.resourceWith(config).allocated.unsafeRunSync()
    client   = startup._1
    shutdown = startup._2.map(_ => minio.stop())

  override def afterAll(): Unit =
    shutdown.unsafeRunSync()

  "minio client" should "connect to a minio installation" in {
      client.bucketExists("not-present") should be(false)
  }
