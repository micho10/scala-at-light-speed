name := "scala-at-light-speed"

version := "0.1"

//scalaVersion := "2.13.6"
scalaVersion := "3.1.1"

  val akkaVersion = "2.6.19"
  val akkaHttpVersion = "10.2.9"

  libraryDependencies ++= Seq(
    // akka streams
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,

    // akka http
//    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
//    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

    // Apache Commons
    "commons-io" % "commons-io" % "2.11.0",
  )
