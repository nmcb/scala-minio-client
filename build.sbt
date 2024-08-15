lazy val root =
  project
    .in(file("."))
    .settings( scalaVersion := "3.4.3"
             , name         := "scala-minio-client"
             , version      := "0.0.1"
             , libraryDependencies ++= Seq(
               "io.minio"       %  "minio"     % "8.5.11",
               "org.scalatest"  %% "scalatest" % "3.2.19" % "test")
    )

scalacOptions ++= Seq(       
  "-encoding", "utf8",        
  "-feature",                 
  "-language:implicitConversions",
  "-language:existentials",
  "-unchecked",
  "-Werror",
  "-deprecation"
)

Compile / run / fork := true
Compile / run / javaOptions ++= Seq("-Xmx8G", "-Xss1G", "-XX:+UseG1GC")

Test / fork := true
Test / javaOptions ++= Seq("-Xmx8G", "-Xss1G", "-XX:+UseG1GC")
