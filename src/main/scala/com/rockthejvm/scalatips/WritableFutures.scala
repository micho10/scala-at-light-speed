package com.rockthejvm.scalatips

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Controllable futures (deterministic)
  * https://blog.rockthejvm.com/controllable-futures/
  */
object WritableFutures:

  val aFuture = Future
    // you have no future, you are DOOMED!
    42 // It'll be evaluated in some thread at some point in time without control over it
    // JK.

  // Futures are inherently non-deterministic

  // target
  // def gimmeMyPreciousValue(yourArg: Int): Future[String] = ???

  // given - multi-threaded service
  object MyService:
    def produceThePreciousValue(theArg: Int): String = "The meaning of your life is " + (theArg / 42)

    def submitTask[A](actualArg: A)(function: A => Unit): Boolean =
      // send the function to be evaluated on some thread, at the discretion of the scheduling logic
      true

  //1) A "production" function which is completely deterministic.
  //2) A submission function which has a pretty terrible API, because the function argument will be evaluated on one of the service's threads and you can't get the returned value back from another thread's call stack.

  //def gimmeMyPreciousValue(yourArg: Int): Future[String] = Future
  //  MyService.produceThePreciousValue(yourArg) // WRONG. Spawning the thread to run the producer function depends on the Service

  // Introducing PROMISES - Controller of a Future

  // Step 1 - create an empty promise
  val myPromise = Promise[String]()
  // Step 2 - extract its future
  val myFuture = myPromise.future
  // Step 3 - consume the future (do your thing with the future, assuming it will be filled with a value at some point)
  val furtherProcessing = myFuture.map(_.toUpperCase())

  // Step 4 - pass the promise to the producer of the value
  def asyncCall(promise: Promise[String]): Unit =
    promise.success("Your value here, your majesty")
  // Step 5 - call the producer
  asyncCall(myPromise)

  // apply this to scenario

  // target
  def gimmeMyPreciousValue(yourArg: Int): Future[String] =
    // step 1 - create promise now
    val thePromise = Promise[String]()

    // step 5 - call the producer
    MyService.submitTask(yourArg) { (x: Int) =>
      val preciousValue = MyService.produceThePreciousValue(x)
      thePromise.success(preciousValue)
    }

    // step 2 - extract the future (return the future now, so it can be reused by whoever's consuming it)
    thePromise.future

  // step 3 - someone will consume my future
  // submit a task to be evaluated later, at the discretion of the service
  // note: if the service is not on the same JVM, you can pass a tuple with the arg and the promise so the service has access to both
