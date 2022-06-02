name := "scala-at-light-speed"

version := "0.1"

scalaVersion := "3.1.2"
crossScalaVersions ++= Seq("2.13.6", "3.1.2")

  val akkaVersion = "2.6.19"
  val akkaHttpVersion = "10.2.9"
  val monocleVersion = "2.0.3"

  libraryDependencies ++= Seq(
    // akka streams
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,

    // akka http
    ("com.typesafe.akka" %% "akka-http" % akkaHttpVersion).cross(CrossVersion.for3Use2_13),
    ("com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion).cross(CrossVersion.for3Use2_13),

    // Apache Commons
    "commons-io" % "commons-io" % "2.11.0",

    // Monocle
    ("com.github.julien-truffaut" %% "monocle-core"  % monocleVersion).cross(CrossVersion.for3Use2_13),
    ("com.github.julien-truffaut" %% "monocle-macro" % monocleVersion).cross(CrossVersion.for3Use2_13),
  )
