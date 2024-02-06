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

  var minio: MinIOContainer = null
  var client: Client        = null
  var shutdown: IO[Unit]    = null

  import cats.effect.unsafe.implicits.global

  def testConfig(server: MinIOContainer): Config =
    Config(
      endpoint  = server.s3URL,
      accessKey = server.userName,
      secretKey = server.password
    )

  override def beforeAll(): Unit =
    val config = Config.load.unsafeRunSync()
    minio  = MinIOContainer(userName = config.accessKey, password = config.secretKey)
    minio.start()

    val startup = Client.resourceWith(testConfig(minio)).allocated.unsafeRunSync()
    client   = startup._1
    shutdown = startup._2

  override def afterAll(): Unit =
    minio.stop()
    shutdown.unsafeRunSync()

  "minio client" should "connect to a minio installation" in {
      client.bucketExists("not-present") should be(false)
  }
