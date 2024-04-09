lazy val root =
  project
    .in(file("."))
    .settings( scalaVersion := "3.4.1"
             , name         := "scala-minio-client"
             , version      := "0.0.1"
             , libraryDependencies ++= Seq(
               "ch.qos.logback"        %  "logback-classic" % "1.4.14",
               "io.minio"              %  "minio"           % "8.5.9",
               "com.github.pureconfig" %% "pureconfig-core" % "0.17.5",
               "org.typelevel"         %% "cats-effect"     % "3.5.3",

               "org.scalatest" %% "scalatest"                      % "3.2.18" % "test",
               "com.dimafeng"  %% "testcontainers-scala-scalatest" % "0.41.2" % "test",
               "com.dimafeng"  %% "testcontainers-scala-minio"     % "0.41.2" % "test")

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
