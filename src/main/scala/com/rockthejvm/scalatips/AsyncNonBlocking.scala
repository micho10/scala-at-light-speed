package com.rockthejvm.scalatips

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.{Future, Promise}

/**
  * Sync, Async, Blocking and Non-Blocking
  * https://blog.rockthejvm.com/sync,-async-and-(non)-blocking/
  */
object AsyncNonBlocking:

  def blockingFunction(arg: Int): Int =
    Thread.sleep(10000)
    arg + 42

  // Synchronous, blocking
  blockingFunction(3) // blocking call
  val theMeaningOfLife = 42 // will wait for 10 seconds before evaluating
  // I don't want to touch on the philosophical, but this happens in real life.

  // Asynchronous, blocking
  import scala.concurrent.ExecutionContext.Implicits.global
  def asyncBlockingFunction(arg: Int): Future[Int] = Future {
    Thread.sleep(10000)
    arg + 42
  }

  asyncBlockingFunction(3)
  val theMeaningOfLife2 = 42 // evaluates immediately

  // Asynchronous, NON-blocking
  def createSimpleActor() = Behaviors.receiveMessage[String] { someMessage =>
    println(s"Received something: $someMessage")
    Behaviors.same
  }

  val rootActor = ActorSystem(createSimpleActor(), "TestSystem") // guardian actor that will create an entire hierarchy
  rootActor ! "Message in a bottle" // enqueueing a message, asynchronous, NON-blocking

  val promiseResolver = ActorSystem(
    Behaviors.receiveMessage[(String, Promise[Int])] {
      case (message, promise) =>
        // do some computation
        promise.success(message.length)
        Behaviors.same
    },
    "promiseResolver"
  )

  def doAsyncNonBlockingThing(arg: String): Future[Int] = {
    val aPromise = Promise[Int]()
    promiseResolver ! (arg, aPromise)
    aPromise.future
  }

  val asyncNonBlockingResult = doAsyncNonBlockingThing("Some message") // Future[Int] - async, non-blocking
  asyncNonBlockingResult.onComplete(value => s"I've got a non-blocking async answer: $value")
