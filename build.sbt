ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

lazy val root = (project in file("."))
  .settings(
    name := "imdb parser"
  )

val AkkaVersion = "2.6.20"
lazy val akkaStream = Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test
)

val AkkaHttpVersion = "10.2.10"
lazy val akkaHttp = Seq(
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)

lazy val alpakkaCsv = Seq(
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "4.0.0",
)

libraryDependencies ++= akkaStream ++ akkaHttp ++ alpakkaCsv