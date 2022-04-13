package com.rockthejvm.scalatips

import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import java.net.URLEncoder
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

/**
  * Akka HTTP in 5 min.
  *
  * https://blog.rockthejvm.com/a-5-minute-akka-http-client/
  */
object AkkaHttp5Mins {

  // TODO: Fix this class to make it Scala 3 compatible

  /**
    * Setup
    *
    * build.sbt
    */
//  val akkaVersion = "2.5.26"
//  val akkaHttpVersion = "10.1.11"
//
//  libraryDependencies ++= Seq(
//    // akka streams
//    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
//    // akka http
//    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
//    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
//  )

  // TODO: Akka HTTP still doesn't support Scala 3
//  implicit val system = ActorSystem() // Akka actors
//  implicit val materializer = ActorMaterializer() // Akka streams
//  import system.dispatcher // "thread pool"

  val source =
  """
    |object SimpleApp {
    |  val aField = 2
    |
    |  def aMethod(x: Int) = x + 1
    |
    |  def main(args: Array[String]) = {
    |    println(aMethod(aField))
    |  }
    |}
  """.stripMargin

//  val request = HttpRequest(
//    method = HttpMethods.POST,
//    uri = "http://markup.su/api/highlighter",
//    entity = HttpEntity(
//      ContentTypes.`application/x-www-form-urlencoded`, // application/json in most cases
//      s"source=${URLEncoder.encode(source.trim, "UTF-8")}&language=Scala&theme=Sunburst" // the actual data you want to send
//    )
//  )
//
//  def sendRequest(): Future[String] = {
//    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
//    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
//    entityFuture.map(entity => entity.data.utf8String)
//  }

//  def simpleRequest() = {
//    val responseFuture = Http().singleRequest(request)
//    responseFuture.flatMap(_.entity.toStrict(2.seconds)).map(_.data.utf8String).foreach(println)
//  }

//def highlightCode(myCode: String): Future[String] =
//  val responseFuture = Http().singleRequest(
//    HttpRequest(
//      method = HttpMethods.POST,
//      uri = "http://markup.su/api/highlighter",
//      entity = HttpEntity(
//        ContentTypes.`application/x-www-form-urlencoded`,
//        s"source=${URLEncoder.encode(myCode.trim, "UTF-8")}&language=Scala&theme=Sunburst"
//      )
//    )
//  )
//
//  responseFuture
//    .flatMap(_.entity.toStrict(2 seconds))
//    .map(_.data.utf8String)

}
