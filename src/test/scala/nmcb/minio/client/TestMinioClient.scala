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

class TestMinioClient extends AsyncFlatSpec
  with Checkpoints
  with Matchers
  with BeforeAndAfterAll
  with Eventually:

  var client: Client     = null
  var shutdown: IO[Unit] = null

  import cats.effect.unsafe.implicits.global

  override def beforeAll(): Unit =
    val startup = Client.load.allocated.unsafeRunSync()
    client   = startup._1
    shutdown = startup._2

  override def afterAll(): Unit =
    shutdown.unsafeRunSync()

  "minio client" should "connect to a minio installation" in {
    client.bucketExists("not-present") should be(false)
  }
