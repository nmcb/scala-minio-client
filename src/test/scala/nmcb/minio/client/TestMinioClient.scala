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
import com.dimafeng.testcontainers.scalatest.*

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
    config = Config.load.unsafeRunSync()

    minio = MinIOContainer(userName = config.accessKey, password = config.secretKey)
    minio.start()
    config = config.copy(endpoint = minio.s3URL)

    println(minio.s3URL)
    val startup = Client.resourceWith(config).allocated.unsafeRunSync()
    client   = startup._1
    shutdown = startup._2

  override def afterAll(): Unit =
    super.afterAll()
    shutdown.unsafeRunSync()

//  "minio server" should "be available" in {
//    withContainers(minio =>
//      println(minio)
//      client.bucketExists("not-present") should be(false)
//    )
//  }

  "minio client" should "connect to a minio installation" in {
//    withContainers(minio =>
      client.bucketExists("not-present") should be(false)
//    )
  }
